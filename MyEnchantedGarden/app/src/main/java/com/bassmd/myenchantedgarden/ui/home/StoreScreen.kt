package com.bassmd.myenchantedgarden.ui.home

import com.bassmd.myenchantedgarden.viewModel.store.StoreViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bassmd.myenchantedgarden.dto.defaultUser
import com.bassmd.myenchantedgarden.ui.HomeBottomBar
import com.bassmd.myenchantedgarden.ui.home.components.StoreItem
import com.bassmd.myenchantedgarden.ui.utils.CustomSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    viewModel: StoreViewModel = koinViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val store = viewModel.userStore.collectAsStateWithLifecycle(initialValue = listOf())
    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle(initialValue = defaultUser)
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
                        text = HomeBottomBar.Store.route,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        .padding(2.dp)
                ) {
                    items(store.value.size) { index ->
                        StoreItem(store.value[index], currentUser.value.coins) { id ->
                            coroutineScope.launch {
                                viewModel.buy(id)
                            }
                        }
                    }
                }
            }
        }
    }
}