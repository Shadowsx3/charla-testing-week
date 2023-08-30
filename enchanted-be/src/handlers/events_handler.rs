use actix_web::{get, web, HttpResponse, Responder, error, Scope};
use crate::middleware::jwt_auth;
use crate::app_config::AppState;
use crate::models::event_model::{Event, filter_event_record, FilteredEvent};

#[get("")]
async fn get_events_handler(
    data: web::Data<AppState>,
    _: jwt_auth::JwtMiddleware,
) -> impl Responder {

    let events: Vec<Event> = sqlx::query_as!(
        Event,
        r#"select * from events order by id"#,
    )
        .fetch_all(&data.db)
        .await
        .unwrap();

    let events_response = events
        .into_iter()
        .map(|e| filter_event_record(&e))
        .collect::<Vec<FilteredEvent>>();

    let json_response = serde_json::json!({
        "events": events_response
    });

    HttpResponse::Ok().json(json_response)
}



pub fn get_scope() -> Scope {
    web::scope("/events")
        .app_data(web::JsonConfig::default().error_handler(|err, _| {
            error::InternalError::from_response(err, HttpResponse::BadRequest().into()).into()
        }))
        .service(get_events_handler)
}