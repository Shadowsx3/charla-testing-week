use actix_web::{error, get, HttpMessage, HttpRequest, HttpResponse, post, Responder, Scope, web};
use chrono::{Duration, Utc};
use serde_json::json;
use crate::app_config::AppState;
use crate::middleware::jwt_auth;
use crate::models::plant_model::{CollectPlantRequest, filter_plant_record, FilteredPlant, Plant};
use crate::models::user_model::{filter_user_record, User};

#[get("")]
async fn get_plants_handler(
    req: HttpRequest,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let plants: Vec<Plant> = sqlx::query_as(r#"
    select plants.*,
    (user_id is not null) as is_unlocked,
    COALESCE(next_collect_time,now()) as next_collect_time
    from plants left join users_plants on plants.id = users_plants.plants_id and (user_id is NULL or user_id = $1)
    order by plants.id"#)
        .bind(user_id)
        .fetch_all(&data.db)
        .await
        .unwrap();

    let plants_response = plants
        .into_iter()
        .map(|p| filter_plant_record(&p))
        .collect::<Vec<FilteredPlant>>();

    let json_response = json!({
        "plants": plants_response
    });

    HttpResponse::Ok().json(json_response)
}

#[post("/collect")]
async fn post_plant_collect_handler(
    req: HttpRequest,
    body: web::Json<CollectPlantRequest>,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let plants: Vec<Plant> = sqlx::query_as(r#"
    select plants.*,
    (user_id is not null) as is_unlocked,
    COALESCE(next_collect_time,now()) as next_collect_time
    from plants left join users_plants on plants.id = users_plants.plants_id
    where user_id is NULL or user_id = $1
    order by plants.id
    "#)
        .bind(user_id)
        .fetch_all(&data.db)
        .await
        .unwrap();

    let mut user = sqlx::query_as!(User, "select * from users where id = $1", user_id)
        .fetch_one(&data.db)
        .await
        .unwrap();

    let available_plants = plants
        .clone()
        .into_iter()
        .filter(|p| p.id == body.id && p.is_unlocked)
        .collect::<Vec<Plant>>();

    if available_plants.is_empty() {
        return HttpResponse::Conflict().json(
            json!({"status": "fail","message": "The plant id is invalid or not unlocked"}),
        );
    }

    let plant = available_plants.first().unwrap();

    if plant.next_collect_time > Utc::now() {
        return HttpResponse::Conflict().json(
            json!({"status": "fail","message": "You have to wait before collecting again"}),
        );
    }

    let new_coins = user.coins + i32::from(plant.coins_to_collect);

    let query_result = sqlx::query!(
        r#"UPDATE users
            SET coins = $1
            WHERE id = $2"#,
        new_coins,
        user_id
    )
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }

    let query_result = sqlx::query!(
        r#"UPDATE users_plants
            SET next_collect_time = $1
            WHERE user_id = $2 and plants_id = $3"#,
        Utc::now() + Duration::minutes(3),
        user_id,
        plant.id
    )
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }

    user.coins = new_coins;

    HttpResponse::Ok().json(json!({
                "user": filter_user_record(&user)
            }
    ))
}

pub fn get_scope() -> Scope {
    web::scope("/plants")
        .app_data(web::JsonConfig::default().error_handler(|err, _| {
            error::InternalError::from_response(err, HttpResponse::BadRequest().into()).into()
        }))
        .service(get_plants_handler)
        .service(post_plant_collect_handler)
}