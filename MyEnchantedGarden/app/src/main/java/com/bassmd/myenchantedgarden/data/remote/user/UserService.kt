package com.bassmd.myenchantedgarden.data.remote.user

import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.dto.UserResponse

interface UserService {
    suspend fun getProfile(): Result<UserResponse>
    suspend fun playGame(playRequest: PlayRequest): Result<UserResponse>
}