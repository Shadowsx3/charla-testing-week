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

#[allow(non_snake_case)]
#[derive(Debug, Serialize)]
pub struct FilteredPlant {
    pub id: i32,
    pub name: String,
    pub description: String,
    pub coinsToCollect: i16,
    pub filePath: String,
    pub isUnlocked: bool,
    pub nextCollectTime: DateTime<Utc>,
}

pub fn filter_plant_record(plant: &Plant) -> FilteredPlant {
    FilteredPlant {
        id: plant.id,
        name: plant.name.to_owned(),
        description: plant.description.to_owned(),
        coinsToCollect: plant.coins_to_collect,
        filePath: plant.file_path.to_owned(),
        isUnlocked: plant.is_unlocked,
        nextCollectTime: plant.next_collect_time,
    }
}