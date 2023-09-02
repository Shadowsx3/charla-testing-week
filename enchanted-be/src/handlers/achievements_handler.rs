use actix_web::{error, get, HttpMessage, HttpRequest, HttpResponse, post, Responder, Scope, web};
use chrono::Utc;
use serde_json::json;
use crate::app_config::AppState;
use crate::middleware::jwt_auth;
use crate::models::achievements_model::{Achievements, AchievementsUnlockRequest, filter_achievement_record, FilteredAchievements};
use crate::models::user_model::User;

#[get("")]
async fn get_achievements_handler(
    req: HttpRequest,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let achievements: Vec<Achievements> = sqlx::query_as(r#"
    select achievements.*,
    (user_id is not null) as is_unlocked
    from achievements left join users_achievements on achievements.id = users_achievements.achievements_id
	and (user_id is NULL or user_id = $1)
    order by achievements.id"#)
        .bind(user_id)
        .fetch_all(&data.db)
        .await
        .unwrap();

    let achievements_responses = achievements
        .into_iter()
        .map(|a| filter_achievement_record(&a))
        .collect::<Vec<FilteredAchievements>>();


    let json_response = json!({
        "achievements": achievements_responses
    });

    HttpResponse::Ok().json(json_response)
}

#[post("/unlock")]
async fn post_achievements_unlock_handler(
    req: HttpRequest,
    body: web::Json<AchievementsUnlockRequest>,
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {
    let ext = req.extensions();
    let user_id = ext.get::<String>().unwrap();

    let achievements: Vec<Achievements> = sqlx::query_as(r#"
    select achievements.*,
    (user_id is not null) as is_unlocked
    from achievements left join users_achievements on achievements.id = users_achievements.achievements_id
	and (user_id is NULL or user_id = $1)
    order by achievements.id
    "#)
        .bind(user_id)
        .fetch_all(&data.db)
        .await
        .unwrap();

    let user = sqlx::query_as!(User, "select * from users where id = $1", user_id)
        .fetch_one(&data.db)
        .await
        .unwrap();

    let available_achievements = achievements
        .clone()
        .into_iter()
        .filter(|a| a.code == body.code && !a.is_unlocked)
        .collect::<Vec<Achievements>>();

    if available_achievements.is_empty() {
        return HttpResponse::Conflict().json(
            json!({"status": "fail","message": "The code is invalid or has already been used"}),
        );
    }

    let achievement = available_achievements.first().unwrap();

    let new_coins = user.coins + achievement.coins;

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
        VALUES ($1, $2, $3)
        ON CONFLICT (user_id,plants_id) DO NOTHING"#,
        user_id,
        achievement.plants_id,
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
        r#"insert into users_achievements (user_id,achievements_id)
        VALUES ($1, $2)"#,
        user_id,
        achievement.id
    )
        .execute(&data.db)
        .await
        .map_err(|err: sqlx::Error| err.to_string());

    if let Err(err) = query_result {
        return HttpResponse::InternalServerError()
            .json(json!({"status": "error","message": format!("{:?}", err)}));
    }

    let new_achievements = achievements
        .into_iter()
        .map(|mut new_a| {
            if new_a.id == achievement.id {
                new_a.is_unlocked = true;
            }
            filter_achievement_record(&new_a)
        }
        ).collect::<Vec<FilteredAchievements>>();

    let json_response = json!({
        "achievements": new_achievements
    });

    HttpResponse::Ok().json(json_response)
}


pub fn get_scope() -> Scope {
    web::scope("/achievements")
        .app_data(web::JsonConfig::default().error_handler(|err, _| {
            error::InternalError::from_response(err, HttpResponse::BadRequest().into()).into()
        }))
        .service(get_achievements_handler)
        .service(post_achievements_unlock_handler)
}