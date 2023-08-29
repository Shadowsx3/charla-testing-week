use actix_web::{get, HttpResponse, Responder, Scope, web};
use serde_json::json;

#[get("/health-checker")]
async fn health_checker_handler() -> impl Responder {
    const MESSAGE: &str = "Yeezy, alive";

    HttpResponse::Ok().json(json!({"status": "success", "message": MESSAGE}))
}

pub fn get_scope() -> Scope {
    web::scope("")
        .service(health_checker_handler)
}