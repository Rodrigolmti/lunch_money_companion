package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.LunchMoneyCompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
@LunchMoneyPreview
private fun BudgetItemPreview() {
    LunchMoneyCompanionTheme {
        Column {
            BudgetItem(fakeBudgetView())
            VerticalSpacer(height = 8.dp)
            BudgetItem(fakeBudgetView(
                recurring = emptyList(),
                items = emptyList()
            ))
            VerticalSpacer(height = 8.dp)
            BudgetItem(fakeBudgetView(
                recurring = emptyList(),
            ))
        }
    }
}

@Composable
internal fun BudgetItem(budget: BudgetView) {
    val expanded = remember { mutableStateOf(false) }

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
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    top = 8.dp
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
                    style = BodyBold,
                )

                if (budget.recurring.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            expanded.value = !expanded.value
                        }
                    ) {
                        Icon(
                            if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = SilverLining,
                        )
                    }
                }
            }

            if (budget.recurring.isEmpty()) {
                VerticalSpacer(height = 16.dp)
            }

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
                            style = Body,
                        )

                        Text(
                            text = it.totalTransactions.toString(),
                            color = White,
                            style = BodyBold,
                        )

                    }

                    VerticalSpacer(height = 16.dp)

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
                            style = Body,
                        )

                        Text(
                            text = formatCurrency(
                                it.totalBudget,
                                it.currency
                            ),
                            color = SunburstGold,
                            style = BodyBold,
                        )

                    }

                    VerticalSpacer(height = 16.dp)

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
                            style = Body,
                        )

                        Text(
                            text = formatCurrency(
                                it.totalSpending,
                                it.currency
                            ),
                            color = if (it.totalSpending > it.totalBudget) FadedBlood else EmeraldSpring,
                            style = BodyBold,
                        )

                    }
                }

            } else {

                Text(
                    stringResource(R.string.budget_empty_budget_message),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = White,
                    style = Body,
                )
            }

            if (budget.recurring.isNotEmpty() && expanded.value) {

                VerticalSpacer(height = 16.dp)

                Text(
                    stringResource(R.string.budget_recurring_label),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = White,
                    style = BodyBold,
                )

                VerticalSpacer(height = 16.dp)

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
                            style = Body,
                        )

                        Text(
                            text = formatCurrency(
                                it.amount,
                                it.currency
                            ),
                            color = SunburstGold,
                            style = BodyBold,
                        )

                    }

                    VerticalSpacer(height = 8.dp)

                    Divider()

                    VerticalSpacer(height = 8.dp)
                }
            }
        }
    }
}