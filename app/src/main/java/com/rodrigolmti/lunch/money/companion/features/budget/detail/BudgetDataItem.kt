package com.rodrigolmti.lunch.money.companion.features.budget.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetItemView
import com.rodrigolmti.lunch.money.companion.features.budget.fakeBudgetItemView
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun BudgetDataItem(
    modifier: Modifier = Modifier,
    item: BudgetItemView? = null,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist
        ),
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = Dp.Hairline,
            color = Color.Black
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                top = CompanionTheme.spacings.spacingD,
                start = CompanionTheme.spacings.spacingD,
                end = CompanionTheme.spacings.spacingD,
                bottom = CompanionTheme.spacings.spacingD
            )
        ) {
            Text(
                text = "Period overview",
                overflow = TextOverflow.Ellipsis,
                color = SunburstGold,
                style = CompanionTheme.typography.bodyBold,
            )

            item?.let {
                VerticalSpacer(CompanionTheme.spacings.spacingD)

                Row {
                    Text(
                        text = stringResource(R.string.budget_transaction_label),
                        style = CompanionTheme.typography.body,
                        color = White,
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = item.totalTransactions.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }

                VerticalSpacer(CompanionTheme.spacings.spacingD)

                Row {
                    Text(
                        stringResource(R.string.budget_value_label),
                        style = CompanionTheme.typography.body,
                        color = White,
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = formatCurrency(
                            item.totalBudget,
                            item.currency
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }

                VerticalSpacer(CompanionTheme.spacings.spacingD)

                Row {
                    Text(
                        stringResource(R.string.budget_spending_label),
                        style = CompanionTheme.typography.body,
                        color = White,
                        modifier = Modifier.weight(1f)
                    )
                    HorizontalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = formatCurrency(
                            item.totalSpending,
                            item.currency
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }
            } ?: run {
                VerticalSpacer(CompanionTheme.spacings.spacingD)

                Text(
                    stringResource(R.string.budget_empty_budget_message),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = White,
                    style = CompanionTheme.typography.body,
                )
            }
        }
    }
}

@Composable
@LunchMoneyPreview
fun BudgetDataItemPreview() {
    CompanionTheme {
        Column {
            BudgetDataItem(
                item = fakeBudgetItemView()
            )

            VerticalSpacer(CompanionTheme.spacings.spacingD)

            BudgetDataItem()
        }
    }
}