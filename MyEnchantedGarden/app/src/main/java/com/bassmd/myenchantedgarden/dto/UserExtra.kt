package com.bassmd.myenchantedgarden.dto

import com.bassmd.myenchantedgarden.repo.dto.UserModel
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: UserModel
)

@Serializable
data class PlayRequest(
    val won: Boolean
)

val defaultUser = UserModel("","",false,"",0,0,0,0, Clock.System.now())