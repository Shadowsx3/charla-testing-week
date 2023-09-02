package com.bassmd.myenchantedgarden.ui.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bassmd.myenchantedgarden.ui.theme.White

@Composable
fun CustomSnackBar(data: SnackbarData) {
    Snackbar(
        modifier = Modifier
            .padding(18.dp),
        containerColor = MaterialTheme.colorScheme.tertiary,
        action = {
        }
    ) {
        Text(
            text = data.visuals.message,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 25.sp,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )
    }
}