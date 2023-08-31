package com.bassmd.myenchantedgarden.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bassmd.myenchantedgarden.R
import com.bassmd.myenchantedgarden.dto.PlantsModel
import com.bassmd.myenchantedgarden.dto.StoreModel
import com.bassmd.myenchantedgarden.dto.getPlantImages
import com.bassmd.myenchantedgarden.koin.appModule
import com.bassmd.myenchantedgarden.ui.auth.RegisterScreen
import com.bassmd.myenchantedgarden.ui.theme.MyEnchantedGardenTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@Composable
fun StoreItem(
    storeModel: StoreModel,
    coins: Int,
    onClick: (Int) -> Unit,
) {
    val state = if (storeModel.isAvailable) "Buy" else "Owned"
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(
                    id = getPlantImages(storeModel.plantFile)
                ),
                contentDescription = storeModel.plantFile,
                modifier = Modifier
                    .size(130.dp)
                    .padding(10.dp),
                contentScale = ContentScale.Fit,
            )
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Top),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 3.dp),
                    text = storeModel.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(vertical = 3.dp),
                    text = storeModel.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(vertical = 3.dp),
                    text = "Cost: ${storeModel.cost}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Button(
                        onClick = { onClick(storeModel.id) },
                        enabled = storeModel.isAvailable && storeModel.cost <= coins
                    ) {
                        Text(
                            text = state,
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
        StoreItem(
            StoreModel(
                id = 1,
                500,
                "Pasas de uva",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "rosa",
                true,
            ), 0
        ) {}
    }
}