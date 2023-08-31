use serde::{Deserialize, Serialize};
use sqlx::FromRow;

#[derive(Debug, Deserialize, Serialize, Clone, FromRow)]
pub struct StoreItem {
    pub id: i32,
    pub cost: i32,
    pub name: String,
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

#[allow(non_snake_case)]
#[derive(Debug, Serialize)]
pub struct FilteredStoreItem {
    pub id: i32,
    pub cost: i32,
    pub name: String,
    pub description: String,
    pub eventId: i32,
    pub plantId: i32,
    pub wasPurchased: bool,
    pub isAvailable: bool,
}

pub fn filter_item_record(store_item: &StoreItem) -> FilteredStoreItem {
    FilteredStoreItem {
        id: store_item.id,
        cost: store_item.cost,
        name: store_item.name.to_owned(),
        description: store_item.description.to_owned(),
        eventId: store_item.event_id,
        plantId: store_item.plant_id,
        wasPurchased: store_item.was_purchased,
        isAvailable: store_item.is_available,
    }
}
