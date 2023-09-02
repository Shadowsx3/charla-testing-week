package com.bassmd.myenchantedgarden.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun PlantItem(
    plantsModel: PlantsModel,
    currentTime: Instant,
    onClick: (Int) -> Unit
) {
    val nextCollect = plantsModel.nextCollectTime - currentTime
    val canCollect = (plantsModel.nextCollectTime - currentTime).inWholeMilliseconds <= 0
    val nextCollectText =
        if (canCollect) "now" else "${nextCollect.inWholeMinutes}m${nextCollect.inWholeSeconds - nextCollect.inWholeMinutes * 60}s"

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .weight(1f),
                    text = plantsModel.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { onClick(plantsModel.id) },
                    enabled = canCollect
                ) {
                    Text(
                        text = "Collect",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = getPlantImages(plantsModel.filePath)
                    ),
                    contentDescription = plantsModel.filePath,
                    modifier = Modifier
                        .size(135.dp),
                    contentScale = ContentScale.Fit,
                )
                Column(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Top),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 3.dp),
                        text = "Coins: ${plantsModel.coinsToCollect}",
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = "Next collect in:\n$nextCollectText",
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = plantsModel.description,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(
    "Home list detail screen",
    showBackground = true
)
@Composable
private fun PreviewLoginScreen() {
    MyEnchantedGardenTheme(dynamicColor = false) {
        PlantItem(
            PlantsModel(
                id = 1,
                "Holiss",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                15,
                "cactus",
                true,
                now().plus(2.minutes).plus(3.seconds)
            ), now()
        ) {}
    }
}