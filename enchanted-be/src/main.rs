use actix_cors::Cors;
use actix_web::middleware::Logger;
use actix_web::{http::header, web, App, HttpServer};
use dotenv::dotenv;
use sqlx::{postgres::PgPoolOptions};
use enchanted_be::app_config::{AppState, Config};
use enchanted_be::route_config;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    if std::env::var_os("RUST_LOG").is_none() {
        std::env::set_var("RUST_LOG", "actix_web=info");
    }
    dotenv().ok();
    env_logger::init();

    let config = Config::init();

    let database_url = std::env::var("DATABASE_URL").expect("DATABASE_URL must be set");
    let pool = match PgPoolOptions::new()
        .max_connections(10)
        .connect(&database_url)
        .await
    {
        Ok(pool) => {
            println!("Connected to the db");
            pool
        }
        Err(err) => {
            println!("Fail...: {:?}", err);
            std::process::exit(1);
        }
    };

    println!("Server started");

    HttpServer::new(move || {
        let cors = Cors::default()
            .allowed_origin("http://localhost:3000")
            .allowed_methods(vec!["GET", "POST"])
            .allowed_headers(vec![
                header::CONTENT_TYPE,
                header::AUTHORIZATION,
                header::ACCEPT,
            ])
            .supports_credentials();
        App::new()
            .app_data(web::Data::new(AppState { db: pool.clone(), env: config.clone() }))
            .configure(route_config::config_app)
            .wrap(cors)
            .wrap(Logger::default())
    })
        .bind(("0.0.0.0", 8080))?
        .run()
        .await
}
