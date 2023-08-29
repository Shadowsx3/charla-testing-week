use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Deserialize, Serialize, Clone, FromRow)]
pub struct StoreItem {
    pub id: i32,
    pub cost: i32,
    pub description: String,
    pub event_id: i32,
    pub plant_id: i32,
    pub was_purchased: bool,
    pub is_available: bool,
}

impl StoreItem {
    pub fn add_account(&mut self, is_available: bool) {
        self.is_available = is_available;
    }
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct StoreItemRequest {
    pub id: i32,
}
