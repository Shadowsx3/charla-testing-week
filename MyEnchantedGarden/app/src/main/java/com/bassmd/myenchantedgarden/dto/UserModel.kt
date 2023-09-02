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
    val energy: Int,
    val coins: Int,
    val wins: Int,
    val losses: Int,
    val nextClaimEnergyTime: Instant,
)