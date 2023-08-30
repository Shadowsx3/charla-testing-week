use chrono::{DateTime, Utc};
use serde::{Deserialize, Serialize};

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct Event {
    pub id: i32,
    pub name: String,
    pub start_date: DateTime<Utc>,
    pub end_date: DateTime<Utc>,
}

#[allow(non_snake_case)]
#[derive(Debug, Serialize)]
pub struct FilteredEvent {
    pub id: i32,
    pub name: String,
    pub startDate: DateTime<Utc>,
    pub endDate: DateTime<Utc>,
}

pub fn filter_event_record(event: &Event) -> FilteredEvent {
    FilteredEvent {
        id: event.id,
        name: event.name.to_owned(),
        startDate: event.start_date,
        endDate: event.end_date,
    }
}