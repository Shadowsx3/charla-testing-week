package com.bassmd.myenchantedgarden.ui.home

import com.bassmd.myenchantedgarden.model.plants.PlantsViewModel
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.home.components.PlantItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: PlantsViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val plants = viewModel.userPlants.collectAsStateWithLifecycle(initialValue = listOf())
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = HomeBottomBar.Plants.route,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                actions = {
                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            coroutineScope.launch {
                                viewModel.updatePlants()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Refresh plants"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                openDialog.value = true
                coroutineScope.launch {
                    viewModel.updatePlants()
                }
            }) {
                Icon(Icons.Filled.PlayArrow, "Play")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                items(plants.value.size) { index ->
                    PlantItem(plants.value[index], viewModel.currentTime) { id ->
                        coroutineScope.launch {
                            val result = viewModel.collect(id)
                            result.onFailure { failure ->
                                Toast.makeText(
                                    context,
                                    failure.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
