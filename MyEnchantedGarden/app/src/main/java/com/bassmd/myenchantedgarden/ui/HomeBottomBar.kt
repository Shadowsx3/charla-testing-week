package com.bassmd.myenchantedgarden.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeBottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Plants : HomeBottomBar(
        route = "PLANTS",
        title = "PLANTS",
        icon = Icons.Default.Favorite
    )

    object Store : HomeBottomBar(
        route = "STORE",
        title = "STORE",
        icon = Icons.Default.ShoppingCart
    )

    object Profile : HomeBottomBar(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.Person
    )
}