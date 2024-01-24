package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun EmptyState(
    message: String,
) {
    Center(
        Modifier.padding(
            start = CompanionTheme.spacings.spacingD,
            end = CompanionTheme.spacings.spacingD,
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_white_coin),
            contentDescription = "White Coin Image",
        )

        VerticalSpacer(height = CompanionTheme.spacings.spacingD)

        Text(
            text = message,
            style = Body,
            color = White,
        )
    }
}

@Composable
@LunchMoneyPreview
private fun ErrorStatePreview() {
    EmptyState(
        message = "Something went wrong",
    )
}
