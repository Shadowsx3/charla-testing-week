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
        "rosa" -> return R.drawable.plants
        "verde" -> return R.drawable.plants
        "aaa" -> return R.drawable.plants
    }
    return 0
}
