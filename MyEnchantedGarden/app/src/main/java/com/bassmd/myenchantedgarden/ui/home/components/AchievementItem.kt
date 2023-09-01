package com.bassmd.myenchantedgarden.ui.home.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bassmd.myenchantedgarden.R
import com.bassmd.myenchantedgarden.dto.AchievementsModel
import com.bassmd.myenchantedgarden.dto.PlantsModel
import com.bassmd.myenchantedgarden.dto.getAchievementImages
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
fun AchievementItem(
    achievementsModel: AchievementsModel
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
        ),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(
                        id = getAchievementImages(achievementsModel.filePath)
                    ),
                    contentDescription = achievementsModel.filePath,
                    modifier = Modifier
                        .size(130.dp),
                    contentScale = ContentScale.Fit,
                )
                Column(
                    Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                        .align(Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 3.dp),
                        text = achievementsModel.name,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 3.dp),
                        text = achievementsModel.description,
                        style = MaterialTheme.typography.bodyLarge,
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
        AchievementItem(
            AchievementsModel(
                "Holis this is a large",
                0,
                "Bien hecho fadsf adsfsa fads fds f dsf sdf ds fsda f asdfsd fadsffasdfas",
                "cactus",
                true,
                1
            )
        )
    }
}