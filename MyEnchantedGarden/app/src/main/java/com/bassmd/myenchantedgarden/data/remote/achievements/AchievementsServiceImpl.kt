package com.bassmd.myenchantedgarden.data.remote.achievements

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.dto.AchievementsResponse
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AchievementsServiceImpl(private val client: HttpClient): AchievementsService {
    override suspend fun getAchievements(): Result<AchievementsResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.ACHIEVEMENTS)
                contentType(ContentType.Application.Json)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unlockAchievement(achievementsRequest: AchievementsRequest): Result<AchievementsResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.ACHIEVEMENTS}/unlock")
                contentType(ContentType.Application.Json)
                body = achievementsRequest
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}