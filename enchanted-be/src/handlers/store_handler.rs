use actix_web::{error, get, HttpMessage, HttpRequest, HttpResponse, post, Responder, Scope, web};
use chrono::Utc;
use serde_json::json;
use crate::app_config::AppState;
use crate::middleware::jwt_auth;
use crate::models::store_handler::{StoreItem, StoreItemRequest};
use crate::models::user_model::User;

#[get("")]
async fn get_store_handler(
    req: HttpRequest,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let store_items: Vec<StoreItem> = sqlx::query_as(r#"
    SELECT store_items.*,
       (user_store_items.user_id IS NOT NULL) AS was_purchased,
       (users_plants.id IS NULL AND user_store_items.user_id IS NULL) AS is_available
    FROM store_items
       LEFT JOIN user_store_items ON store_items.id = user_store_items.store_items_id AND user_store_items.user_id = $1
       LEFT JOIN users_plants ON store_items.plant_id = users_plants.plants_id AND users_plants.user_id = $1
    ORDER BY store_items.id
    "#)
        .bind(user_id)
        .fetch_all(&data.db)
        .await
        .unwrap();

    let json_response = json!({
        "store": store_items
    });

    HttpResponse::Ok().json(json_response)
}

#[post("/buy")]
async fn post_buy_item_handler(
    req: HttpRequest,
    body: web::Json<StoreItemRequest>,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let store_items: Vec<StoreItem> = sqlx::query_as(r#"
    SELECT store_items.*,
       (user_store_items.user_id IS NOT NULL) AS was_purchased,
       (users_plants.id IS NULL AND user_store_items.user_id IS NULL) AS is_available
    FROM store_items
       LEFT JOIN user_store_items ON store_items.id = user_store_items.store_items_id AND user_store_items.user_id = $1
       LEFT JOIN users_plants ON store_items.plant_id = users_plants.plants_id AND users_plants.user_id = $1
    ORDER BY store_items.id
    "#)
        .bind(user_id)
        .fetch_all(&data.db)
        .await
        .unwrap();

    let user = sqlx::query_as!(User, "select * from users where id = $1", user_id)
        .fetch_one(&data.db)
        .await
        .unwrap();

    let available_items = store_items
        .clone()
        .into_iter()
        .filter(|item| item.id == body.id && item.is_available)
        .collect::<Vec<StoreItem>>();

    if available_items.is_empty() {
        return HttpResponse::Conflict().json(
            json!({"status": "fail","message": "The item doesn't exist or is not available"}),
        );
    }

    let item = available_items.first().unwrap();

    let new_coins = user.coins - item.cost;

    if new_coins < 0 {
        return HttpResponse::Conflict().json(
            json!({"status": "fail","message": "Do not have enough money for this item"}),
        );
    }

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
        r#"insert into users_plants (user_id,plants_id,next_collect_time)
        VALUES ($1, $2, $3)"#,
        user_id,
        item.plant_id,
        Utc::now()
    )
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }


    let query_result = sqlx::query!(
        r#"insert into user_store_items (user_id,store_items_id)
        VALUES ($1, $2)"#,
        user_id,
        body.id
    )
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }

    let new_store_items = store_items
        .into_iter()
        .map(|mut new_item|
            if new_item.id == item.id {
                new_item.is_available = false;
                new_item.was_purchased = true;
                new_item
            } else { new_item }
        ).collect::<Vec<StoreItem>>();

    let json_response = json!({
        "store": new_store_items
    });

    HttpResponse::Ok().json(json_response)
}

pub fn get_scope() -> Scope {
    web::scope("/store")
        .app_data(web::JsonConfig::default().error_handler(|err, _| {
            error::InternalError::from_response(err, HttpResponse::BadRequest().into()).into()
        }))
        .service(get_store_handler)
        .service(post_buy_item_handler)
}