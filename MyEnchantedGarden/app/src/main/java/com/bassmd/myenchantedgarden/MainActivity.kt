package com.bassmd.myenchantedgarden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.bassmd.myenchantedgarden.graphs.RootNavigationGraph
import com.bassmd.myenchantedgarden.ui.theme.MyEnchantedGardenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyEnchantedGardenTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}