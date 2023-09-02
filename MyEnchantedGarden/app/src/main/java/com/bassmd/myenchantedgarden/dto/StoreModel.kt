package com.bassmd.myenchantedgarden.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class StoreModel(
    val id: Int,
    val cost: Int,
    val name: String,
    val description: String,
    val plantFile: String,
    val isAvailable: Boolean,
)
