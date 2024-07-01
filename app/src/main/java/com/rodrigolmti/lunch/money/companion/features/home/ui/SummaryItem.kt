package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.home.model.PeriodSummaryView
import com.rodrigolmti.lunch.money.companion.features.home.model.fakePeriodSummaryView
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun SummaryItem(summary: PeriodSummaryView) {
    Column(
        modifier = Modifier
            .padding(CompanionTheme.spacings.spacingD),
    ) {
        Text(
            stringResource(R.string.home_period_label),
            color = White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompanionTheme.typography.body
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        HorizontalDivider()
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        Text(
            stringResource(R.string.home_income_label),
            color = White,
            style = CompanionTheme.typography.bodyBold
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        Row {
            Text(
                stringResource(R.string.home_total_income_label),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                color = SunburstGold,
                style = CompanionTheme.typography.bodyBold,
            )
            Text(
                formatCurrency(summary.totalIncome, summary.currency),
                style = CompanionTheme.typography.body,
                color = White,
            )
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        HorizontalDivider()
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        Text(
            stringResource(R.string.home_expenses_label),
            color = White,
            style = CompanionTheme.typography.bodyBold
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        Row {
            Text(
                stringResource(R.string.home_total_expenses_label),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                color = SunburstGold,
                style = CompanionTheme.typography.bodyBold,
            )
            Text(
                formatCurrency(summary.totalExpense, summary.currency),
                style = CompanionTheme.typography.body,
                color = White,
            )
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        Row {
            Text(
                stringResource(R.string.home_net_income_label),
                color = White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                style = CompanionTheme.typography.body
            )
            Text(
                formatCurrency(summary.netIncome, summary.currency),
                color = if (summary.savingsRate < 0) FadedBlood else EmeraldSpring,
                style = CompanionTheme.typography.body
            )
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        HorizontalDivider()
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        Row {
            Text(
                stringResource(R.string.home_savings_rate_label),
                color = White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                style = CompanionTheme.typography.body
            )
            Text(
                "${summary.savingsRate}%",
                color = if (summary.savingsRate < 0) FadedBlood else EmeraldSpring,
                style = CompanionTheme.typography.body
            )
        }
    }
}

@Composable
@LunchMoneyPreview
private fun SummaryItemPreview() {
    CompanionTheme {
        SummaryItem(
            fakePeriodSummaryView()
        )
    }
}