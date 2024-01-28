package com.rodrigolmti.lunch.money.companion.features.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.BTC_WALLET
import com.rodrigolmti.lunch.money.companion.core.BUY_ME_A_BOOK_URL
import com.rodrigolmti.lunch.money.companion.core.utils.copyToClipboard
import com.rodrigolmti.lunch.money.companion.core.utils.openUrl
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun DonationBottomSheet(
    onDismiss: () -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(CompanionTheme.spacings.spacingD),
    ) {
        Text(
            stringResource(R.string.donation_title),
            style = CompanionTheme.typography.header,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        Text(
            stringResource(R.string.donation_description),
            style = CompanionTheme.typography.body,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingE)
        LunchButton(
            label = stringResource(R.string.donation_btc_action),
        ) {
            context.copyToClipboard(
                "Bitcoin Wallet",
                BTC_WALLET
            )
            onDismiss()
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        LunchButton(
            label = stringResource(R.string.donation_book_action),
        ) {
            context.openUrl(BUY_ME_A_BOOK_URL)
            onDismiss()
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingF)
    }
}