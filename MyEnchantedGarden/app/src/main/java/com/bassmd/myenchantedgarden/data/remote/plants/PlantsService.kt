package com.bassmd.myenchantedgarden.data.remote.plants

import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import com.bassmd.myenchantedgarden.dto.UserResponse

interface PlantsService {
    suspend fun getPlants(): Result<PlantsResponse>
    suspend fun collectPlant(plantRequest: PlantRequest): Result<UserResponse>
}