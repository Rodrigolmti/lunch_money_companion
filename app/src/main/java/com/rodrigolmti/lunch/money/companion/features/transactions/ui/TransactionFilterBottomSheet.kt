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
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
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
        modifier = Modifier.padding(16.dp)
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

            HorizontalSpacer(width = 8.dp)

            Text(
                text = label,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                color = White,
                style = Header,
            )

            HorizontalSpacer(width = 8.dp)

            IconButton(onClick = onNextMonthClick) {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = SilverLining,
                )
            }
        }

        VerticalSpacer(height = 32.dp)

        LunchButton(
            label = "Filter",
        ) {
            onFilter()
        }

        VerticalSpacer(height = 64.dp)
    }
}

@Composable
@LunchMoneyPreview
fun TransactionFilterBottomSheetPreview() {
    TransactionFilterBottomSheet("January 2024")
}