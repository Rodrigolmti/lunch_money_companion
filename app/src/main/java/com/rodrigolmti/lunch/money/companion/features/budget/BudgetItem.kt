@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
@LunchMoneyPreview
private fun BudgetItemPreview() {
    CompanionTheme {
        Column {
            BudgetItem(fakeBudgetView())
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            BudgetItem(
                fakeBudgetView(
                    recurring = emptyList(),
                    items = emptyList()
                )
            )
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            BudgetItem(
                fakeBudgetView(
                    recurring = emptyList(),
                )
            )
        }
    }
}

@Composable
internal fun BudgetItem(budget: BudgetView) {
    val expanded = remember { mutableStateOf(false) }
    val hasRecurring = budget.recurring.isNotEmpty()

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
            .clickable {
                expanded.value = !expanded.value
            }
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(
                    start = CompanionTheme.spacings.spacingD,
                    end = CompanionTheme.spacings.spacingD,
                    bottom = CompanionTheme.spacings.spacingD,
                    top = if (hasRecurring) CompanionTheme.spacings.spacingB else CompanionTheme.spacings.spacingD,
                ),
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

                if (hasRecurring) {
                    Icon(
                        if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        modifier = Modifier
                            .size(CompanionTheme.spacings.spacingF),
                        contentDescription = null,
                        tint = SilverLining,
                    )
                }
            }

            VerticalSpacer(height = CompanionTheme.spacings.spacingD)


            if (budget.items.isNotEmpty()) {

                budget.items.forEach {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Text(
                            stringResource(R.string.budget_transaction_label),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier.weight(1f),
                            color = White,
                            style = CompanionTheme.typography.body,
                        )

                        Text(
                            text = it.totalTransactions.toString(),
                            color = White,
                            style = CompanionTheme.typography.bodyBold,
                        )

                    }

                    VerticalSpacer(height = CompanionTheme.spacings.spacingD)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Text(
                            stringResource(R.string.budget_value_label),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier.weight(1f),
                            color = White,
                            style = CompanionTheme.typography.body,
                        )

                        Text(
                            text = formatCurrency(
                                it.totalBudget,
                                it.currency
                            ),
                            color = SunburstGold,
                            style = CompanionTheme.typography.bodyBold,
                        )

                    }

                    VerticalSpacer(height = CompanionTheme.spacings.spacingD)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Text(
                            stringResource(R.string.budget_spending_label),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier.weight(1f),
                            color = White,
                            style = CompanionTheme.typography.body,
                        )

                        Text(
                            text = formatCurrency(
                                it.totalSpending,
                                it.currency
                            ),
                            color = if (it.totalSpending > it.totalBudget) FadedBlood else EmeraldSpring,
                            style = CompanionTheme.typography.bodyBold,
                        )

                    }
                }

            } else {

                Text(
                    stringResource(R.string.budget_empty_budget_message),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = White,
                    style = CompanionTheme.typography.body,
                )
            }

            if (hasRecurring && expanded.value) {

                VerticalSpacer(height = CompanionTheme.spacings.spacingD)

                Text(
                    stringResource(R.string.budget_recurring_label),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                )

                VerticalSpacer(height = CompanionTheme.spacings.spacingD)

                budget.recurring.forEach {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Text(
                            text = it.payee,
                            color = White,
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = CompanionTheme.typography.body,
                        )

                        Text(
                            text = formatCurrency(
                                it.amount,
                                it.currency
                            ),
                            color = SunburstGold,
                            style = CompanionTheme.typography.bodyBold,
                        )

                    }

                    VerticalSpacer(height = CompanionTheme.spacings.spacingB)

                    Divider()

                    VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                }
            }
        }
    }
}