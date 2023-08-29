package com.bassmd.myenchantedgarden.data.remote

import android.util.Log
import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import com.bassmd.myenchantedgarden.data.remote.dto.LoginResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthServiceImpl(
    private val client: HttpClient
) : AuthService {

    override suspend fun loginUser(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            Result.success(client.post {
                url(HttpRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                body = loginRequest
            })
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Result.failure(e)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Result.failure(e)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}