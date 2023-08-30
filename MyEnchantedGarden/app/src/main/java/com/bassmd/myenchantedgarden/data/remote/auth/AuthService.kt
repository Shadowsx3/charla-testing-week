package com.bassmd.myenchantedgarden.data.remote.auth
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.UserResponse

interface AuthService {
    suspend fun register(registerRequest: RegisterRequest): Result<StatusModel>
    suspend fun loginUser(loginRequest: LoginRequest): Result<UserResponse>
    suspend fun logOut(): Result<StatusModel>
}