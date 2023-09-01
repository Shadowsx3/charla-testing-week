package com.bassmd.myenchantedgarden.dto

import com.bassmd.myenchantedgarden.R
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

fun getAchievementImages(filePath: String): Int {
    when (filePath) {
        "bank" -> return R.drawable.bank
        "box" -> return R.drawable.box
        "cake" -> return R.drawable.cake
        "coffee" -> return R.drawable.coffee
        "color" -> return R.drawable.color
        "compass" -> return R.drawable.compass
        "diamond" -> return R.drawable.diamond
        "dice" -> return R.drawable.dice
        "game" -> return R.drawable.game
        "microscope" -> return R.drawable.microscope
        "money" -> return R.drawable.money
        "tribal" -> return R.drawable.tribal
    }
    return R.drawable.pumpkin
}