package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun BudgetItem(
    budget: BudgetView,
    onItemClick: (BudgetView) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist
        ),
        modifier = modifier
//            .clickable {
//                onItemClick(budget)
//            }
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(CompanionTheme.spacings.spacingD),
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    budget.category,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.weight(1f),
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                )

//                HorizontalSpacer(width = CompanionTheme.spacings.spacingB)
//
//                Icon(
//                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                    contentDescription = null,
//                    tint = SilverLining,
//                )
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun BudgetItemPreview() {
    CompanionTheme {
        Column {
            BudgetItem(fakeBudgetView(), {})
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            BudgetItem(
                fakeBudgetView(
                ),
                {}
            )
        }
    }
}