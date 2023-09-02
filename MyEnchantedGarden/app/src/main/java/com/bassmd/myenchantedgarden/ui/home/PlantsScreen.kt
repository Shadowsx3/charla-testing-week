package com.bassmd.myenchantedgarden.ui.home

import com.bassmd.myenchantedgarden.model.plants.PlantsViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bassmd.myenchantedgarden.R
import com.bassmd.myenchantedgarden.repo.dto.AchievementsCodes
import com.bassmd.myenchantedgarden.repo.dto.UserModel
import com.bassmd.myenchantedgarden.dto.defaultUser
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.home.components.PlantItem
import com.bassmd.myenchantedgarden.ui.utils.CustomSnackBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsScreen(
    viewModel: PlantsViewModel = koinViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val plants = viewModel.userPlants.collectAsStateWithLifecycle(initialValue = listOf())
    val achievements =
        viewModel.userAchievements.collectAsStateWithLifecycle(initialValue = listOf())
    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle(initialValue = defaultUser)
    val (openDialogState, setOpenDialogState) = remember { mutableStateOf(false) }
    val currentTime = remember { mutableStateOf(now()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val appError =
        viewModel.currentAppError.collectAsStateWithLifecycle()
    if (appError.value.showError) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = appError.value.message,
                duration = appError.value.duration
            )
            viewModel.dismissError()
        }
    }

    if (plants.value.isNotEmpty()) {
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(1000)
                currentTime.value = now()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                CustomSnackBar(data)
            }
        },
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
            )
        },
        floatingActionButton = {
            if (plants.value.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = {
                        setOpenDialogState(true)
                    }) {
                    Icon(Icons.Filled.PlayArrow, "Play")
                    Text(text = "Play")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center),
            ) {
                if (openDialogState) {
                    PlayDialog(currentUser, {
                        setOpenDialogState(false)
                        viewModel.showError("AI: I always win 😄")
                    }, {
                        coroutineScope.launch {
                            viewModel.play(it)
                            setOpenDialogState(false)
                        }
                    })
                }
            }
        }
        if (plants.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = Color.Transparent,
                    )
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plant_alone),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            text = "Try to go to the store first",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (achievements.value.find { a -> a.name == "I LOVE COFFEE" } == null) {
                                coroutineScope.launch {
                                    viewModel.unlock(AchievementsCodes.COFFEE)
                                }
                            } else {
                                viewModel.showError("☕\nWith these coins we can buy coffee")
                            }

                        },
                    shape = MaterialTheme.shapes.medium.copy(
                        all = CornerSize(0.dp)
                    ),
                    border = BorderStroke(width = 2.dp, color = Color.DarkGray),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            text = "Coins: ${currentUser.value.coins}"
                        )
                    }
                }
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(corner = CornerSize(6.dp)),
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color.DarkGray
                    )
                )
                {
                    LazyColumn(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        items(plants.value.size) { index ->
                            PlantItem(plants.value[index], currentTime.value) { id ->
                                coroutineScope.launch {
                                    viewModel.collect(id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PlayDialog(
    userModel: State<UserModel>,
    onDismissRequest: () -> Unit,
    onPlay: (Boolean) -> Unit
) {
    val nextPlayIn =
        remember { mutableStateOf(userModel.value.nextClaimEnergyTime - now()) }
    if (userModel.value.energy == 0) {
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(1000)
                nextPlayIn.value = userModel.value.nextClaimEnergyTime - now()
            }
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
            ),
            border = BorderStroke(4.dp, Color.Black)
        ) {
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chess",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (userModel.value.energy != 0) {
                        "Energy: ${userModel.value.energy}"
                    } else if (nextPlayIn.value.inWholeSeconds <= 0) {
                        "*You take a nap and wake up energized*"
                    } else {
                        val nextIn = nextPlayIn.value
                        val minutes = nextIn.inWholeMinutes
                        "Next play in:${minutes}m${nextIn.inWholeSeconds - minutes * 60}s"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.chess),
                    contentDescription = "chess image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(130.dp),
                )
                Text(
                    text = "*You got bored in the garden*",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "*And ended up playing the best AI chess*",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = if (userModel.value.isPremium) "Do you win or lose?" else "Do you draw or lose?",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 22.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onPlay(false) }
                    ) {
                        Text(
                            text = "Lose",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp
                        )
                    }
                    if (userModel.value.isPremium) {
                        Button(
                            onClick = {
                                onPlay(true)
                            },
                        ) {
                            Text(
                                text = "Win",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp
                            )
                        }
                    } else {
                        Button(
                            onClick = onDismissRequest
                        ) {
                            Text(
                                text = "Draw",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 22.sp
                            )
                        }
                    }
                }
            }
        }
    }
}