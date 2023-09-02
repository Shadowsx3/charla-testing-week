package com.bassmd.myenchantedgarden.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bassmd.myenchantedgarden.repo.dto.StoreModel
import com.bassmd.myenchantedgarden.dto.getPlantImages
import com.bassmd.myenchantedgarden.ui.theme.MyEnchantedGardenTheme

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
                    modifier = Modifier.padding(vertical = 3.dp).fillMaxWidth(),
                    text = storeModel.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(vertical = 3.dp),
                    text = "Price: ${storeModel.cost}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    modifier = Modifier.padding(vertical = 3.dp),
                    text = storeModel.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Button(
                        shape = RoundedCornerShape(12.dp),
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