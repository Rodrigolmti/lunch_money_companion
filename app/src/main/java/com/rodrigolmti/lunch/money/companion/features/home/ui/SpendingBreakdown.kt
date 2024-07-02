package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.home.model.SpendingBreakdownView
import com.rodrigolmti.lunch.money.companion.features.home.model.fakeSpendingBreakdownView
import com.rodrigolmti.lunch.money.companion.uikit.components.CustomProgressBar
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.ExpenseColors
import com.rodrigolmti.lunch.money.companion.uikit.theme.IncomeColors
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun SpendingBreakdown(breakdown: SpendingBreakdownView) {
    Column(
        modifier = Modifier
            .padding(CompanionTheme.spacings.spacingD),
    ) {
        Text(
            stringResource(R.string.home_spending_breakdown_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = SunburstGold,
            style = CompanionTheme.typography.bodyBold,
        )

        if (breakdown.incomes.isNotEmpty()) {
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            HorizontalDivider()
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            Text(
                stringResource(R.string.home_income_label),
                color = White,
                style = CompanionTheme.typography.bodyBold
            )
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            breakdown.incomes.forEach { item ->
                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                Row {
                    Text(
                        item.name,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.width(90.dp),
                        color = SunburstGold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CompanionTheme.typography.bodyBold,
                    )
                    HorizontalSpacer(width = CompanionTheme.spacings.spacingD)
                    CustomProgressBar(
                        color = IncomeColors.random(),
                        percentage = item.percentage
                    )
                }
            }
        }

        if (breakdown.expenses.isNotEmpty()) {
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            HorizontalDivider()
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)
            Text(
                stringResource(R.string.home_expenses_label),
                color = White,
                style = CompanionTheme.typography.bodyBold
            )
            VerticalSpacer(height = CompanionTheme.spacings.spacingB)

            breakdown.expenses.forEach { item ->
                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                Row {
                    Text(
                        item.name,
                        modifier = Modifier.width(90.dp),
                        textAlign = TextAlign.Start,
                        color = SunburstGold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CompanionTheme.typography.bodyBold,
                    )
                    HorizontalSpacer(width = CompanionTheme.spacings.spacingD)
                    CustomProgressBar(
                        color = ExpenseColors.random(),
                        percentage = item.percentage
                    )
                }
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun SpendingBreakdownPreview() {
    CompanionTheme {
        SpendingBreakdown(
            breakdown = fakeSpendingBreakdownView()
        )
    }
}