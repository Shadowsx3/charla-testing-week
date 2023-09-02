package com.bassmd.myenchantedgarden.data.remote.auth

import com.bassmd.myenchantedgarden.data.remote.HttpRoutes
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import kotlinx.serialization.decodeFromString
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class AuthServiceImpl(
    private val client: HttpClient,
    private val json: Json
) : AuthService {
    override suspend fun register(registerRequest: RegisterRequest): Result<StatusModel> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.AUTH}/register")
                contentType(Json)
                body = registerRequest
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

    override suspend fun loginUser(loginRequest: LoginRequest): Result<UserResponse> {
        return try {
            Result.success(client.post {
                url("${HttpRoutes.AUTH}/login")
                contentType(Json)
                body = loginRequest
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

    override suspend fun logOut(): Result<StatusModel> {
        return try {
            Result.success(client.get() {
                url("${HttpRoutes.AUTH}/logout")
                contentType(Json)
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