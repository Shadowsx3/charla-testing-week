package com.bassmd.myenchantedgarden

import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import com.bassmd.myenchantedgarden.data.remote.dto.UserResponse

interface UserRepository {
   suspend fun loginUser(loginRequest: LoginRequest): UserResponse
}
