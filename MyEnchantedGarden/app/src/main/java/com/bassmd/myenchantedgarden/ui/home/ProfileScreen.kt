package com.bassmd.myenchantedgarden.ui.home

import com.bassmd.myenchantedgarden.model.profile.ProfileViewModel
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bassmd.myenchantedgarden.R
import com.bassmd.myenchantedgarden.dto.AchievementsCodes
import com.bassmd.myenchantedgarden.dto.StoreModel
import com.bassmd.myenchantedgarden.dto.UserModel
import com.bassmd.myenchantedgarden.dto.defaultUser
import com.bassmd.myenchantedgarden.graphs.Graph
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.auth.components.SimpleOutlinedTextField
import com.bassmd.myenchantedgarden.ui.home.components.AchievementItem
import com.bassmd.myenchantedgarden.ui.home.components.PlantItem
import com.bassmd.myenchantedgarden.ui.home.components.StoreItem
import com.bassmd.myenchantedgarden.ui.theme.MyEnchantedGardenTheme
import com.bassmd.myenchantedgarden.ui.utils.CustomSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = koinViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val achievements =
        viewModel.userAchievements.collectAsStateWithLifecycle(initialValue = listOf())
    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle(initialValue = defaultUser)
    val (openDialogState, setOpenDialogState) = remember { mutableStateOf(false) }
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

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                CustomSnackBar(data)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = HomeBottomBar.Profile.route,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
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
                                val result = viewModel.signOut()
                                if (result) {
                                    navController.navigate(Graph.ROOT) {
                                        popUpTo(0)
                                    }
                                }
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Refresh plants"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    setOpenDialogState(true)
                }) {
                Icon(Icons.Filled.Build, "Enter code")
                Text(text = "Unlock")
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
                if (viewModel.isBusy) {
                    CircularProgressIndicator()
                }
                if (openDialogState) {
                    CodeDialog({ setOpenDialogState(false) }, {
                        coroutineScope.launch {
                            viewModel.unlock(it)
                            setOpenDialogState(false)
                        }
                    })
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile(Modifier.clickable {
                    if (achievements.value.find { a -> a.name == "Lets play" } == null) {
                        coroutineScope.launch {
                            viewModel.unlock(AchievementsCodes.GAME)
                        }
                    } else {
                        viewModel.showError("🦔\nShe is indeed a cute hedgehog\n💘")

                    }
                })
                CreateInfo(currentUser)
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
                    if (achievements.value.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 30.dp),
                                text = "Try to find achievement codes",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                            Image(
                                painter = painterResource(id = R.drawable.bamboo),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(130.dp),
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            items(achievements.value.size) { index ->
                                AchievementItem(achievements.value[index])
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .padding(top = 10.dp)
            .size(160.dp)
            .padding(2.dp),
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.DarkGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.hedgehog),
            contentDescription = "profile image",
            modifier = modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CreateInfo(userModel: State<UserModel>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = userModel.value.name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 45.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Text(
            text = if (userModel.value.isPremium) "⭐Premium⭐" else "Free-User",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 25.sp,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Wins: ${userModel.value.wins}",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
            )
            Text(
                text = "Losses: ${userModel.value.losses}",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun CodeDialog(onDismissRequest: () -> Unit, onUnlock: (String) -> Unit) {
    val code = remember { mutableStateOf("") }
    Dialog(onDismissRequest = { }) {
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
                    text = "Achievements Unlock",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SimpleOutlinedTextField("Enter Code", code.value) { code.value = it }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Button(
                        onClick = { onUnlock(code.value) },
                    ) {
                        Text(
                            text = "Unlock",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    "Home list detail screen", showBackground = true
)
@Composable
private fun PreviewLoginScreen() {
    MyEnchantedGardenTheme(dynamicColor = false) {
        CodeDialog(
            {}, {}
        )
    }
}