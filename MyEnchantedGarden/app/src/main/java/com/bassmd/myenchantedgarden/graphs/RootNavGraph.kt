package com.bassmd.myenchantedgarden.graphs

import HomeScreen
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.auth.LoginScreen
import com.bassmd.myenchantedgarden.ui.auth.RegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    navBarController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION,
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen(
                navBarController
            ) {
                navBarController.navigate(HomeBottomBar.Plants.route) {
                    popUpTo(navBarController.graph.startDestinationId) {
                        inclusive = true
                        saveState = false
                    }
                    restoreState = false
                }
                navController.navigate(Graph.AUTHENTICATION)
            }
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}