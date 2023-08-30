package com.bassmd.myenchantedgarden.ui.auth


import LoginViewModel
import RegisterViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bassmd.myenchantedgarden.R
import com.bassmd.myenchantedgarden.ui.theme.MyEnchantedGardenTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = koinViewModel()) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
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
            Box(
                modifier = Modifier
                    .align(Alignment.Center),
            ) {
                if (viewModel.isRegistered) {
                    Text(
                        text = viewModel.isRegistered.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
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
                        .padding(start = 20.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.plants),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(70.dp))
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
                SimpleOutlinedTextField(email) { email = it }

                Spacer(modifier = Modifier.padding(3.dp))
                SimpleOutlinedPasswordTextField(password) { password = it }
                Spacer(modifier = Modifier.padding(10.dp))
                GradientButton(
                    gradientColors = listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    ),
                    cornerRadius = 16.dp,
                    nameButton = "Login",
                    roundedCornerShape = RoundedCornerShape(
                        topStart = 30.dp,
                        bottomEnd = 30.dp
                    ),
                    onClick = {
                        coroutineScope.launch {
                            viewModel.register(email, name, password)
                        }
                    }
                )
                TextButton(
                    modifier = Modifier.padding(start = 15.dp, end = 1.dp),
                    onClick = {},
                ) {
                    Text(
                        text = "Create An Account",
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                TextButton(
                    modifier = Modifier.padding(start = 1.dp),
                    onClick = {},
                ) {
                    Text(
                        text = "Reset Password",
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge,
                    )
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
    MyEnchantedGardenTheme(dynamicColor = false) {
        RegisterScreen()
    }
}