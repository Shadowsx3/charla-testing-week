package com.bassmd.myenchantedgarden

import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest

interface UserRepository {
   suspend fun loginUser(loginRequest: LoginRequest)
}
