use actix_web::{get, web, HttpMessage, HttpRequest, HttpResponse, Responder, error, Scope, post};
use chrono::{Duration, Utc};
use serde_json::json;
use crate::middleware::jwt_auth;
use crate::models::user_model::{filter_user_record, PlayRequest, User};
use crate::app_config::AppState;

#[get("/me")]
async fn get_me_handler(
    req: HttpRequest,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let user = sqlx::query_as!(User, "select * from users where id = $1", user_id)
        .fetch_one(&data.db)
        .await
        .unwrap();

    let json_response = json!({
                "user": filter_user_record(&user)
            }
    );

    HttpResponse::Ok().json(json_response)
}


#[post("/play")]
async fn post_user_play_handler(
    req: HttpRequest,
    body: web::Json<PlayRequest>,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let mut user = sqlx::query_as!(User, "select * from users where id = $1", user_id)
        .fetch_one(&data.db)
        .await
        .unwrap();

    if user.energy <= 0 {
        if user.next_claim_energy_time <= Utc::now() {
            user.energy = 100;
            user.next_claim_energy_time = Utc::now() + Duration::minutes(5)
        } else {
            return HttpResponse::Conflict().json(
                json!({"status": "fail","message": "You have to wait"}),
            );
        }
    }

    if body.won {
        user.wins += 1;
        user.coins += 1000;
    } else {
        user.losses += 1;
        user.energy -= 10;
        user.coins -= 500;
    }

    let query_result = sqlx::query!(
        r#"UPDATE users
            SET coins = $1, wins = $2, losses = $3, energy = $4, next_claim_energy_time = $5
            WHERE id = $6"#,
        user.coins,
        user.wins,
        user.losses,
        user.energy,
        user.next_claim_energy_time,
        user_id
    )
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }

    HttpResponse::Ok().json(json!({
                "user": filter_user_record(&user)
            }
    ))
}


pub fn get_scope() -> Scope {
    web::scope("/user")
        .app_data(web::JsonConfig::default().error_handler(|err, _| {
            error::InternalError::from_response(err, HttpResponse::BadRequest().into()).into()
        }))
        .service(get_me_handler)
        .service(post_user_play_handler)
}