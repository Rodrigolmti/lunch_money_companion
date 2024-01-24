package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
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
            .padding(CompanionTheme.spacings.spacingD),
    ) {
        Text(
            title,
            style = CompanionTheme.typography.header,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        Text(
            message,
            style = CompanionTheme.typography.body,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingE)
        LunchButton(
            label = "OK",
        ) {
            onBottomSheetDismissed()
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingF)
    }
}