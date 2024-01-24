package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.Header
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun TransactionFilterBottomSheet(
    label: String,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
    onFilter: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(CompanionTheme.spacings.spacingD)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPreviousMonthClick) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
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
                style = Header,
            )

            HorizontalSpacer(width = CompanionTheme.spacings.spacingB)

            IconButton(onClick = onNextMonthClick) {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = SilverLining,
                )
            }
        }

        VerticalSpacer(height = CompanionTheme.spacings.spacingF)

        LunchButton(
            label = "Filter",
        ) {
            onFilter()
        }

        VerticalSpacer(height = CompanionTheme.spacings.spacingI)
    }
}

@Composable
@LunchMoneyPreview
fun TransactionFilterBottomSheetPreview() {
    TransactionFilterBottomSheet("January 2024")
}