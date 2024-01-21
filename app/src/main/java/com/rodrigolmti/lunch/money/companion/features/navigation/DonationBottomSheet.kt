package com.rodrigolmti.lunch.money.companion.features.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.BTC_WALLET
import com.rodrigolmti.lunch.money.companion.core.utils.copyToClipboard
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.Header
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun DonationBottomSheet(
    onDismiss: () -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            stringResource(R.string.donation_title),
            style = Header,
            color = White,
        )
        VerticalSpacer(height = 16.dp)
        Text(
            stringResource(R.string.donation_description),
            style = Body,
            color = White,
        )
        VerticalSpacer(height = 24.dp)
        LunchButton(
            label = stringResource(R.string.donation_action),
        ) {
            context.copyToClipboard(
                "Bitcoin Wallet",
                BTC_WALLET
            )
            onDismiss()
        }
        VerticalSpacer(height = 32.dp)
    }
}