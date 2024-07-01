package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold

@Composable
fun CustomProgressBar(
    color: Color,
    percentage: Int,
    modifier: Modifier = Modifier,
) {
    val finalPercentage = percentage.coerceIn(0, 100)
    val progressWidth = if (finalPercentage <= 0) 1f else finalPercentage.toFloat()

    Box(
        modifier = modifier
            .height(CompanionTheme.spacings.spacingC)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = MidnightSlate,
            )
    ) {
        if (progressWidth > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progressWidth / 100f)
                    .height(CompanionTheme.spacings.spacingE)
                    .background(color, shape = RoundedCornerShape(CompanionTheme.spacings.spacingC))
            )
        }
    }
}

@Composable
@LunchMoneyPreview
fun CustomProgressBarPreview() {
    CompanionTheme {
        Column {
            CustomProgressBar(color = EmeraldSpring, percentage = 100)

            VerticalSpacer(height = CompanionTheme.spacings.spacingD)

            CustomProgressBar(color = FadedBlood, percentage = 30)

            VerticalSpacer(height = CompanionTheme.spacings.spacingD)

            CustomProgressBar(color = SunburstGold, percentage = 0)
        }
    }
}
