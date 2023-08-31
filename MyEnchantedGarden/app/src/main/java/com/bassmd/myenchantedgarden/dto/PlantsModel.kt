package com.bassmd.myenchantedgarden.dto

import com.bassmd.myenchantedgarden.R
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
data class PlantsModel(
    val id: Int,
    val name: String,
    val description: String,
    val coinsToCollect: Int,
    val filePath: String,
    val isUnlocked: Boolean,
    val nextCollectTime: Instant
)


@Serializable
data class PlantsResponse(
    val plants: List<PlantsModel>
)

@Serializable
data class PlantRequest(
    val id: Int
)

fun getPlantImages(filePath: String): Int {
    when (filePath) {
        "bamboo" -> return R.drawable.bamboo
        "cactus" -> return R.drawable.cactus
        "coffee_beans" -> return R.drawable.coffee_beans
        "leaf" -> return R.drawable.leaf
        "mushroom" -> return R.drawable.mushroom
        "plant_alone" -> return R.drawable.plant_alone
        "pumpkin" -> return R.drawable.pumpkin
        "rose" -> return R.drawable.rose
        "sunflower" -> return R.drawable.sunflower
        "tulip" -> return R.drawable.tulip
        "waterlily" -> return R.drawable.waterlily
        "wheat" -> return R.drawable.wheat
    }
    return 0
}
