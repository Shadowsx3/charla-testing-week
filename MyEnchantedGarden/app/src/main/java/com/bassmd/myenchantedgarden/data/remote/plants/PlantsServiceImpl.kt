package com.bassmd.myenchantedgarden.data.remote.plants

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import com.bassmd.myenchantedgarden.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PlantsServiceImpl(private val client: HttpClient) : PlantsService {
    override suspend fun getPlants(): Result<PlantsResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.PLANTS)
                contentType(ContentType.Application.Json)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun collectPlant(plantRequest: PlantRequest): Result<UserResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.PLANTS}/collect")
                contentType(ContentType.Application.Json)
                body = plantRequest
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}