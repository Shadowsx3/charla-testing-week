use actix_web::web;
use crate::handlers::{user_handler, auth_handler, status_handler, events_handler, plants_handler, achievements_handler, store_handler};

pub fn config_app(cfg: &mut web::ServiceConfig) {
    cfg.service(
        web::scope("/api")
            .service(user_handler::get_scope())
            .service(auth_handler::get_scope())
            .service(events_handler::get_scope())
            .service(achievements_handler::get_scope())
            .service(store_handler::get_scope())
            .service(plants_handler::get_scope())
            .service(status_handler::get_scope())
    );
}