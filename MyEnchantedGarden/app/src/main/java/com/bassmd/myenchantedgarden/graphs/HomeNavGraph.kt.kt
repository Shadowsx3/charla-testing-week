package com.bassmd.myenchantedgarden.graphs

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.home.PlantsScreen
import com.bassmd.myenchantedgarden.ui.home.ProfileScreen
import com.bassmd.myenchantedgarden.ui.home.StoreScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeBottomBar.Plants.route
    ) {
        composable(route = HomeBottomBar.Plants.route) {
            PlantsScreen(navController)
        }
        composable(route = HomeBottomBar.Store.route) {
            StoreScreen(navController)
        }
        composable(route = HomeBottomBar.Profile.route) {
            ProfileScreen(navController)
        }/*
        detailsNavGraph(navController = navController)*/
    }
}
 /*
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}*/