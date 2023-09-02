package com.bassmd.myenchantedgarden.data.remote.achievements

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.dto.AchievementsResponse
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import com.bassmd.myenchantedgarden.dto.StatusModel
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AchievementsServiceImpl(private val client: HttpClient, private val json: Json) :
    AchievementsService {
    override suspend fun getAchievements(): Result<AchievementsResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.ACHIEVEMENTS)
                contentType(ContentType.Application.Json)
            })
        } catch (e: ResponseException) {
            try {
                val errorString = e.response.receive<String>()
                val errorModel: StatusModel = json.decodeFromString(string = errorString)
                return Result.failure(Error(errorModel.message))
            } catch (ex: Exception) {
                return Result.failure(ex)
            }
        } catch (e: Exception) {
            return Result.failure(Exception("Unknown bug"))
        }
    }

    override suspend fun unlockAchievement(achievementsRequest: AchievementsRequest): Result<AchievementsResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.ACHIEVEMENTS}/unlock")
                contentType(ContentType.Application.Json)
                body = achievementsRequest
            })
        } catch (e: ResponseException) {
            try {
                val errorString = e.response.receive<String>()
                val errorModel: StatusModel = json.decodeFromString(string = errorString)
                return Result.failure(Error(errorModel.message))
            } catch (ex: Exception) {
                return Result.failure(ex)
            }
        } catch (e: Exception) {
            return Result.failure(Exception("Unknown bug"))
        }
    }
}