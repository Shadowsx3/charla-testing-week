package com.bassmd.myenchantedgarden.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val body: String
)