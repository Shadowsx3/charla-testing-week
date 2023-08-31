package com.bassmd.myenchantedgarden.data.remote.store

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.EventModel
import com.bassmd.myenchantedgarden.dto.EventResponse
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.dto.StoreResponse
import com.bassmd.myenchantedgarden.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class StoreServiceImpl(private val client: HttpClient): StoreService {
    override suspend fun getEvents(): Result<EventResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.EVENTS)
                contentType(ContentType.Application.Json)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStore(): Result<StoreResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.STORE)
                contentType(ContentType.Application.Json)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun buyItem(storeRequest: StoreRequest): Result<StoreResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.STORE}/buy")
                contentType(ContentType.Application.Json)
                body = storeRequest
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}