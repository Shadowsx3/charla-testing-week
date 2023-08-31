package com.bassmd.myenchantedgarden.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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

@Composable
fun PlantItem(
    plantsModel: PlantsModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(
                    id = getPlantImages(plantsModel.filePath)
                ),
                contentDescription = plantsModel.filePath,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Top)
            ) {
                Text(
                    text = plantsModel.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = plantsModel.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
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
                "rosa",
                true,
                Clock.System.now()
            )
        ) {}
    }
}