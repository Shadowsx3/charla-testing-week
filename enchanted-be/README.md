docker-compose up -d

sqlx database create / drop
sqlx migrate run
sqlx migrate revert
cargo watch -q -c -w src/ -x run
