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

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeBottomBar.Home.route
    ) {
        composable(route = HomeBottomBar.Home.route) {
            TextButton(
                onClick = {
                    navController.navigate(Graph.HOME)
                }
            ){
                Text(text = HomeBottomBar.Home.route,)
            }
        }
        composable(route = HomeBottomBar.Profile.route) {
            TextButton(
                onClick = {
                    navController.navigate(Graph.HOME)
                }
            ){
                Text(text = HomeBottomBar.Profile.route,)
            }
        }
        composable(route = HomeBottomBar.Settings.route) {
            TextButton(
                onClick = {
                    navController.navigate(Graph.HOME)
                }
            ){
                Text(text = HomeBottomBar.Settings.route,)
            }
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