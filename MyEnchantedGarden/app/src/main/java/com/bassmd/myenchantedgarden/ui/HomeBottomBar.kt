package com.bassmd.myenchantedgarden.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeBottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : HomeBottomBar(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    object Profile : HomeBottomBar(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.Person
    )

    object Settings : HomeBottomBar(
        route = "SETTINGS",
        title = "SETTINGS",
        icon = Icons.Default.Settings
    )
}