package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.Header
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun BottomSheetComponent(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    onBottomSheetDismissed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            title,
            style = Header,
            color = White,
        )
        VerticalSpacer(height = 16.dp)
        Text(
            message,
            style = Body,
            color = White,
        )
        VerticalSpacer(height = 24.dp)
        LunchButton(
            label = "OK",
        ) {
            onBottomSheetDismissed()
        }
        VerticalSpacer(height = 32.dp)
    }
}