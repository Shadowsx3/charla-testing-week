package com.bassmd.myenchantedgarden.ui.auth

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bassmd.myenchantedgarden.R
import com.bassmd.myenchantedgarden.graphs.AuthScreen
import com.bassmd.myenchantedgarden.graphs.Graph
import com.bassmd.myenchantedgarden.koin.appModule
import com.bassmd.myenchantedgarden.viewModel.login.LoginViewModel
import com.bassmd.myenchantedgarden.ui.auth.components.GradientButton
import com.bassmd.myenchantedgarden.ui.auth.components.SimpleOutlinedPasswordTextField
import com.bassmd.myenchantedgarden.ui.auth.components.SimpleOutlinedTextField
import com.bassmd.myenchantedgarden.ui.theme.MyEnchantedGardenTheme
import com.bassmd.myenchantedgarden.ui.utils.CustomSnackBar
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    val activity = (LocalContext.current as? Activity)
    val coroutineScope = rememberCoroutineScope()
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

    BackHandler(true) {
        activity?.finish()
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState) { data ->
            CustomSnackBar(data)
        }
    }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier.align(Alignment.TopCenter),
            ) {
                Box(
                    modifier = Modifier.align(Alignment.Center),
                ) {
                    if (viewModel.isBusy) {
                        CircularProgressIndicator()
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🌺\nMy Enchanted Garden",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.plants),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Sign In",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SimpleOutlinedTextField(
                        "Enter Email",
                        viewModel.email.value
                    ) { viewModel.email.value = it }

                    Spacer(modifier = Modifier.padding(3.dp))
                    SimpleOutlinedPasswordTextField(viewModel.password.value) {
                        viewModel.password.value = it
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    GradientButton(gradientColors = listOf(
                        MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary
                    ),
                        cornerRadius = 16.dp,
                        nameButton = "Login",
                        roundedCornerShape = RoundedCornerShape(
                            topStart = 30.dp, bottomEnd = 30.dp
                        ),
                        onClick = {
                            coroutineScope.launch {
                                val isLogged = viewModel.signIn()
                                if (isLogged) {
                                    navController.popBackStack()
                                    navController.navigate(Graph.HOME) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        })
                    TextButton(
                        modifier = Modifier.padding(start = 15.dp, end = 10.dp),
                        onClick = {
                            navController.navigate(AuthScreen.SignUp.route) {
                                popUpTo(AuthScreen.Login.route) {
                                    inclusive = true
                                }
                            }
                        },
                    ) {
                        Text(
                            text = "Create An Account",
                            letterSpacing = 1.sp,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                }


            }

        }
    }
}

@Preview(
    "Home list detail screen",
    device = Devices.PIXEL_4_XL,
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PreviewLoginScreen() {
    startKoin {
        androidLogger()
        modules(appModule)
    }
    MyEnchantedGardenTheme(dynamicColor = false) {
        LoginScreen(rememberNavController())
    }
}