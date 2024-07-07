package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun MonthSelector(
    label: String,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPreviousMonthClick) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = SilverLining,
            )
        }

        HorizontalSpacer(width = CompanionTheme.spacings.spacingB)

        Text(
            text = label,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
            color = White,
            style = CompanionTheme.typography.header,
        )

        HorizontalSpacer(width = CompanionTheme.spacings.spacingB)

        IconButton(onClick = onNextMonthClick) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = SilverLining,
            )
        }
    }
}

@Composable
@LunchMoneyPreview
fun MonthSelectorPreview() {
    CompanionTheme {
        MonthSelector(
            label = "January 2024",
            onPreviousMonthClick = {},
            onNextMonthClick = {},
        )
    }
}