package com.bassmd.myenchantedgarden.dto

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
data class UserModel(
    val email: String,
    val name: String,
    val isPremium: Boolean,
    val role: String,
    val energy: Byte,
    val coins: Int,
    val wins: Int,
    val losses: Int,
    val nextClaimEnergyTime: Instant,
)

@Serializable
data class UserResponse(
    val user: UserModel
)

@Serializable
data class PlayRequest(
    val won: Boolean
)

val defaultUser = UserModel("","",false,"",0,0,0,0,now())