package com.bassmd.myenchantedgarden.data.remote

import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import com.bassmd.myenchantedgarden.data.remote.dto.RegisterRequest
import com.bassmd.myenchantedgarden.data.remote.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthServiceImpl(
    private val client: HttpClient
) : AuthService {
    override suspend fun register(registerRequest: RegisterRequest): Result<UserResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(loginRequest: LoginRequest): Result<UserResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.AUTH}/login")
                contentType(ContentType.Application.Json)
                body = loginRequest
            })
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Result.failure(e)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Result.failure(e)
        } catch (e:  ServerResponseException) {
            // 5xx - responses
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logOut(): Result<String> {
        TODO("Not yet implemented")
    }
}