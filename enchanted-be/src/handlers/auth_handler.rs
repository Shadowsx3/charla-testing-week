use actix_web::{cookie::{time::Duration as ActixWebDuration, Cookie}, get, post, web, HttpResponse, Responder, error, Scope};
use argon2::{
    password_hash::{rand_core::OsRng, PasswordHash, PasswordHasher, PasswordVerifier, SaltString},
    Argon2,
};
use chrono::{prelude::*, Duration};
use jsonwebtoken::{encode, EncodingKey, Header};
use serde_json::json;
use sqlx::Row;
use serde_variant::to_variant_name;
use crate::middleware::jwt_auth;
use crate::models::user_model::{filter_user_record, LoginUserSchema, RegisterUserRequest, TokenClaims, User};
use crate::app_config::AppState;

#[post("/register")]
async fn register_user_handler(
    body: web::Json<RegisterUserRequest>,
    data: web::Data<AppState>,
) -> impl Responder {
    let exists: bool = sqlx::query("SELECT EXISTS(SELECT 1 FROM users WHERE email = $1)")
        .bind(body.email.to_owned().to_lowercase())
        .fetch_one(&data.db)
        .await
        .unwrap()
        .get(0);

    if exists {
        return HttpResponse::Conflict().json(
            serde_json::json!({"status": "fail","message": "User with that email already exists"}),
        );
    }

    let salt = SaltString::generate(&mut OsRng);
    let hashed_password = Argon2::default()
        .hash_password(body.password.as_bytes(), &salt)
        .expect("Error while hashing password")
        .to_string();
    let user_id = uuid::Uuid::new_v4().to_string();
    let next_claim = Utc::now();
    let query_result = sqlx::query(
        r#"INSERT INTO users (id,email,name,role,password,next_claim_energy_time,picture_id) VALUES ($1, $2, $3, $4, $5, $6, 1)"#
    )
        .bind(user_id.to_owned())
        .bind(body.email.to_string().to_lowercase())
        .bind(body.name.to_string())
        .bind(to_variant_name(&body.role).unwrap())
        .bind(hashed_password)
        .bind(next_claim)
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        if err.contains("Duplicate entry") {
            return HttpResponse::BadRequest().json(
                json!({"status": "fail","message": "User with that email already exists"}),
            );
        }

        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }

    let query_result = sqlx::query_as!(User, r#"SELECT * FROM users WHERE id = $1"#, user_id)
        .fetch_one(&data.db)
        .await;

    match query_result {
        Ok(user) => {
            let user_response = json!({
                "user": filter_user_record(&user)
            });

            return HttpResponse::Ok().json(user_response);
        }
        Err(e) => {
            return HttpResponse::InternalServerError()
                .json(json!({"status": "error","message": format!("{:?}", e)}));
        }
    }
}

#[post("/login")]
async fn login_user_handler(
    body: web::Json<LoginUserSchema>,
    data: web::Data<AppState>,
) -> impl Responder {
    let query_result = sqlx::query_as!(User, "SELECT * FROM users WHERE email = $1", body.email)
        .fetch_optional(&data.db)
        .await
        .unwrap();

    let is_valid = query_result.to_owned().map_or(false, |user| {
        let parsed_hash = PasswordHash::new(&user.password).unwrap();
        Argon2::default()
            .verify_password(body.password.as_bytes(), &parsed_hash)
            .map_or(false, |_| true)
    });

    if !is_valid {
        return HttpResponse::BadRequest()
            .json(json!({"status": "fail", "message": "Invalid email or password"}));
    }

    let user = query_result.unwrap();

    let now = Utc::now();
    let iat = now.timestamp() as usize;
    let exp = (now + Duration::minutes(60)).timestamp() as usize;
    let claims: TokenClaims = TokenClaims {
        sub: user.id.to_string(),
        exp,
        iat,
    };

    let token = encode(
        &Header::default(),
        &claims,
        &EncodingKey::from_secret(data.env.jwt_secret.as_ref()),
    )
        .unwrap();

    let cookie = Cookie::build("token", token.to_owned())
        .path("/")
        .max_age(ActixWebDuration::new(60 * 60, 0))
        .http_only(true)
        .finish();

    HttpResponse::Ok()
        .cookie(cookie)
        .json(json!({"user":  filter_user_record(&user)}))
}

#[get("/logout")]
async fn logout_handler(_: jwt_auth::JwtMiddleware) -> impl Responder {
    let cookie = Cookie::build("token", "")
        .path("/")
        .max_age(ActixWebDuration::new(-1, 0))
        .http_only(true)
        .finish();

    HttpResponse::Ok()
        .cookie(cookie)
        .json(json!({"status": "success"}))
}

pub fn get_scope() -> Scope {
    web::scope("/auth")
        .app_data(web::JsonConfig::default().error_handler(|err, _| {
            error::InternalError::from_response(err, HttpResponse::BadRequest().into()).into()
        }))
        .service(register_user_handler)
        .service(login_user_handler)
        .service(logout_handler)
}
