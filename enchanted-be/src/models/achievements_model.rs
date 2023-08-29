use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Deserialize, Serialize, Clone, FromRow)]
pub struct Achievements {
    pub id: i32,
    pub name: String,
    pub coins: i32,
    pub code: String,
    pub description: String,
    pub file_path: String,
    pub is_unlocked: bool,
    pub plants_id: i32
}

#[allow(non_snake_case)]
#[derive(Debug, Serialize)]
pub struct FilteredAchievements {
    pub name: String,
    pub coins: i32,
    pub description: String,
    pub file_path: String,
    pub is_unlocked: bool,
    pub plants_id: i32
}

pub fn filter_achievement_record(achievements: &Achievements) -> FilteredAchievements {
    FilteredAchievements {
        name: achievements.name.to_owned(),
        is_unlocked: achievements.is_unlocked,
        coins: achievements.coins,
        description: achievements.description.to_owned(),
        file_path: achievements.file_path.to_owned(),
        plants_id: achievements.plants_id
    }
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct AchievementsUnlockRequest {
    pub code: String
}