package com.bassmd.myenchantedgarden.data.remote.store

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.EventModel
import com.bassmd.myenchantedgarden.dto.EventResponse
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsResponse
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.dto.StoreResponse
import com.bassmd.myenchantedgarden.dto.UserResponse
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

class StoreServiceImpl(private val client: HttpClient,  private val json: Json): StoreService {
    override suspend fun getEvents(): Result<EventResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.EVENTS)
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

    override suspend fun getStore(): Result<StoreResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.STORE)
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

    override suspend fun buyItem(storeRequest: StoreRequest): Result<StoreResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.STORE}/buy")
                contentType(ContentType.Application.Json)
                body = storeRequest
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