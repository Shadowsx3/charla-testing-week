package com.bassmd.myenchantedgarden.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
data class StoreItem(
    val id: Int,
    val cost: Int,
    val name: String,
    val description: String,
    val eventId: Int,
    val plantId: Int,
    val wasPurchased: Boolean,
    val isAvailable: Boolean,
)

@Serializable
data class StoreResponse(
    val store: List<StoreItem>
)

@Serializable
data class StoreRequest(
    val id: Int
)

@Serializable
data class EventModel(
    val id: Int,
    val name: String,
    val startDate: Instant,
    val endDate: Instant,
)

@Serializable
data class EventResponse(
    val events: List<EventModel>
)
