package com.bassmd.myenchantedgarden.data.remote.achievements

import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.dto.AchievementsResponse
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import com.bassmd.myenchantedgarden.dto.UserResponse

interface AchievementsService {
    suspend fun getAchievements(): Result<AchievementsResponse>
    suspend fun unlockAchievement(achievementsRequest: AchievementsRequest): Result<AchievementsResponse>
}