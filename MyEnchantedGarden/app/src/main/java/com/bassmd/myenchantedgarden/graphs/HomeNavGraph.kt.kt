package com.bassmd.myenchantedgarden.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.home.PlantsScreen
import com.bassmd.myenchantedgarden.ui.home.ProfileScreen
import com.bassmd.myenchantedgarden.ui.home.StoreScreen

@Composable
fun HomeNavGraph(navController: NavHostController, oldNavHostController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeBottomBar.Plants.route
    ) {
        composable(route = HomeBottomBar.Plants.route) {
            PlantsScreen()
        }
        composable(route = HomeBottomBar.Store.route) {
            StoreScreen()
        }
        composable(route = HomeBottomBar.Profile.route) {
            ProfileScreen(oldNavHostController)
        }
    }
}