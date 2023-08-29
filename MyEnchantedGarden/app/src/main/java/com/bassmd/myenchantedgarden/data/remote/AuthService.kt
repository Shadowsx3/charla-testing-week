package com.bassmd.myenchantedgarden.data.remote
import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import com.bassmd.myenchantedgarden.data.remote.dto.LoginResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface AuthService {

    suspend fun loginUser(loginRequest: LoginRequest): Result<LoginResponse>
}