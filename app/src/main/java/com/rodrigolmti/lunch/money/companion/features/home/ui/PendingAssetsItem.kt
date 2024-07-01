package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.ACCOUNTS_URL
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.openUrl
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun PendingAssetsItem(
    assets: List<String>,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(CompanionTheme.spacings.spacingD)
    ) {
        Text(
            stringResource(R.string.home_pending_label),
            color = White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompanionTheme.typography.body
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        HorizontalDivider()
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        Text(
            stringResource(R.string.home_pending_description),
            color = White,
            modifier = Modifier.fillMaxWidth(),
            style = CompanionTheme.typography.body
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        assets.forEach { asset ->
            Text(
                text = asset,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SunburstGold,
                style = CompanionTheme.typography.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        LunchButton(
            label = stringResource(R.string.home_reconnect_now_action),
        ) {
            context.openUrl(ACCOUNTS_URL)
        }
    }
}

@Composable
@LunchMoneyPreview
private fun PendingAssetsItemPreview() {
    CompanionTheme {
        PendingAssetsItem(
            assets = listOf(
                "cibc",
                "td",
            )
        )
    }
}