package com.bassmd.myenchantedgarden.dto

import androidx.compose.material3.SnackbarDuration

data class AppError(
    val message: String = "",
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val showError: Boolean
)
