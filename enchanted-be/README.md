docker-compose up -d


sqlx migrate run
sqlx migrate revert
cargo watch -q -c -w src/ -x run
