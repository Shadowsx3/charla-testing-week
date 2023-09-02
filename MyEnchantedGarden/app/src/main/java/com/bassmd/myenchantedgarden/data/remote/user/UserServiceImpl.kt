package com.bassmd.myenchantedgarden.data.remote.user

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
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

class UserServiceImpl(private val client: HttpClient,  private val json: Json): UserService {
    override suspend fun getProfile(): Result<UserResponse> {
        return try {
            Result.success(client.get {
                url("${HttpRoutes.USER}/me")
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

    override suspend fun playGame(playRequest: PlayRequest): Result<UserResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.USER}/play")
                contentType(ContentType.Application.Json)
                body = playRequest
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