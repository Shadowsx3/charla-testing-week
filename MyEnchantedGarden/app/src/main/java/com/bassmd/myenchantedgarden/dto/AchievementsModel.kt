package com.bassmd.myenchantedgarden.dto

import kotlinx.serialization.Serializable

@Serializable
data class AchievementsModel(
    val name: String,
    val coins: Int,
    val description: String,
    val filePath: String,
    val isUnlocked: Boolean,
    val plantsId: Int
)


@Serializable
data class AchievementsResponse(
    val achievements: List<AchievementsModel>
)

@Serializable
data class AchievementsRequest(
    val code: String
)