package com.bassmd.myenchantedgarden.data.remote
import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import com.bassmd.myenchantedgarden.data.remote.dto.RegisterRequest
import com.bassmd.myenchantedgarden.data.remote.dto.UserResponse

interface AuthService {
    suspend fun register(registerRequest: RegisterRequest): Result<UserResponse>
    suspend fun loginUser(loginRequest: LoginRequest): Result<UserResponse>
    suspend fun logOut(): Result<String>
}