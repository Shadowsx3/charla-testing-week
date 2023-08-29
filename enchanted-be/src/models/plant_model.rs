use chrono::{DateTime, Utc};
use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Deserialize, Serialize, Clone, FromRow)]
pub struct Plant {
    pub id: i32,
    pub name: String,
    pub description: String,
    pub coins_to_collect: i16,
    pub file_path: String,
    pub is_unlocked: bool,
    pub next_collect_time: DateTime<Utc>,
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct CollectPlantRequest {
    pub id: i32,
}