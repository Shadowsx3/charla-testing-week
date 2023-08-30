package com.bassmd.myenchantedgarden.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PlantsModel(
    val id: Int,
    val name: String,
    val description: String,
    val coinsToCollect: Int,
    val filePath: String,
    val isUnlocked: Boolean,
    val nextCollectTime: LocalDateTime
)


@Serializable
data class PlantsResponse(
    val plants: List<PlantsModel>
)

@Serializable
data class PlantRequest(
    val id: Int
)
