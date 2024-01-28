@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.features.recurring

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.TropicalLagoon
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
@LunchMoneyPreview
private fun RecurringItemPreview() {
    CompanionTheme {
        Column {
            RecurringItem(fakeRecurringView())
        }
    }
}

@Composable
internal fun RecurringItem(budget: RecurringView) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist
        ),
        border = BorderStroke(
            width = Dp.Hairline,
            color = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(CompanionTheme.spacings.spacingD)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = budget.payee,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.weight(1f),
                    color = White,
                    style = CompanionTheme.typography.body,
                )

                HorizontalSpacer(CompanionTheme.spacings.spacingB)

                Text(
                    text = formatCurrency(
                        budget.amount,
                        budget.currency
                    ),
                    color = if (budget.amount > 0) EmeraldSpring else FadedBlood,
                    style = CompanionTheme.typography.bodyBold,
                )
            }

            VerticalSpacer(height = CompanionTheme.spacings.spacingB)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column {
                    Text(
                        text = budget.billingDate,
                        color = White,
                        style = CompanionTheme.typography.body,
                    )

                    Text(
                        text = budget.cadence,
                        color = TropicalLagoon,
                        style = CompanionTheme.typography.body,
                    )

                }

                HorizontalSpacer(CompanionTheme.spacings.spacingB)

                Text(
                    text = getRecurringTypeLabel(budget.type),
                    color = getRecurringTypeColor(budget.type),
                    style = CompanionTheme.typography.body,
                )
            }
        }
    }
}

private fun getRecurringTypeColor(type: RecurringViewType): Color = when (type) {
    RecurringViewType.CLEARED -> SunburstGold
    RecurringViewType.SUGGESTED -> TropicalLagoon
}

@Composable
private fun getRecurringTypeLabel(type: RecurringViewType): String = when (type) {
    RecurringViewType.CLEARED -> stringResource(id = R.string.recurring_cleared_label)
    RecurringViewType.SUGGESTED -> stringResource(id = R.string.recurring_suggested_label)
}