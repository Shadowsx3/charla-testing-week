package com.bassmd.myenchantedgarden.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatusModel(
    val status: String,
    val message: String = ""
)
