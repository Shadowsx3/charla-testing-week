package com.bassmd.myenchantedgarden.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StoreModel(
    val id: Int,
    val cost: Int,
    val description: String,
    val eventId: Int,
    val plantId: Int,
    val wasPurchased: Int,
    val isAvailable: Boolean,
)

@Serializable
data class StoreResponse(
    val store: List<StoreModel>
)

@Serializable
data class StoreRequest(
    val id: Int
)

@Serializable
data class EventModel(
    val id: Int,
    val name: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)

@Serializable
data class EventResponse(
    val events: List<EventModel>
)