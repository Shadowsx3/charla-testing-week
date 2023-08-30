package com.bassmd.myenchantedgarden.data.remote.user

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserServiceImpl(private val client: HttpClient): UserService {
    override suspend fun getProfile(): Result<UserResponse> {
        return try {
            Result.success(client.get {
                url(HttpRoutes.USER)
                contentType(ContentType.Application.Json)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun playGame(playRequest: PlayRequest): Result<UserResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.USER}/play")
                contentType(ContentType.Application.Json)
                body = playRequest
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}