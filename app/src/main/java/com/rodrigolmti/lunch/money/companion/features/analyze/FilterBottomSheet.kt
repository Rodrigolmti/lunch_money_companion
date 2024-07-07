@file:OptIn(ExperimentalLayoutApi::class)

package com.rodrigolmti.lunch.money.companion.features.analyze

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.FilterPreset
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.MonthSelector
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
@LunchMoneyPreview
fun FilterBottomSheetPreview() {
    CompanionTheme {
        Column {
            FilterBottomSheet(
                selected = FilterPreset.LAST_7_DAYS,
                onFilterSelected = {},
                onFilter = {},
                label = "January 2024"
            )
            HorizontalSpacer(width = CompanionTheme.spacings.spacingD)
            FilterBottomSheet(
                selected = FilterPreset.CUSTOM,
                onFilterSelected = {},
                onFilter = {},
                label = "January 2024"
            )
        }
    }
}

@Composable
fun FilterBottomSheet(
    label: String,
    selected: FilterPreset,
    onFilterSelected: (FilterPreset) -> Unit,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
    onFilter: () -> Unit,
    bottomSpacing: Dp = CompanionTheme.spacings.spacingD
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = CompanionTheme.spacings.spacingD,
                end = CompanionTheme.spacings.spacingD,
                top = CompanionTheme.spacings.spacingD,
                bottom = bottomSpacing,
            ),
    ) {
        Text(
            stringResource(R.string.filter_title),
            style = CompanionTheme.typography.header,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        Text(
            stringResource(R.string.filter_description),
            style = CompanionTheme.typography.body,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingE)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingA)
        ) {
            FilterPreset.entries.forEach {
                FilterChip(
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SunburstGold,
                        selectedLabelColor = MidnightSlate,
                    ),
                    selected = it == selected,
                    onClick = {
                        onFilterSelected(it)
                    },
                    label = {
                        Text(getLabelByPreset(it))
                    },
                )
            }
        }

        if (selected == FilterPreset.CUSTOM) {
            VerticalSpacer(height = CompanionTheme.spacings.spacingE)

            MonthSelector(
                label = label,
                onPreviousMonthClick = onPreviousMonthClick,
                onNextMonthClick = onNextMonthClick,
            )
        }

        VerticalSpacer(height = CompanionTheme.spacings.spacingF)

        LunchButton(
            label = stringResource(R.string.filter_action),
        ) {
            onFilter()
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingE)
    }
}

@Composable
private fun getLabelByPreset(preset: FilterPreset): String {
    return when (preset) {
        FilterPreset.MONTH_TO_DATE -> stringResource(R.string.filter_month_to_date)
        FilterPreset.YEAR_TO_DATE -> stringResource(R.string.filter_year_to_date)
        FilterPreset.LAST_7_DAYS -> stringResource(R.string.filter_last_seven_days)
        FilterPreset.LAST_30_DAYS -> stringResource(R.string.filter_last_thirty_days)
        FilterPreset.LAST_MONTH -> stringResource(R.string.filter_last_month)
        FilterPreset.LAST_3_MONTHS -> stringResource(R.string.filter_last_three_months)
        FilterPreset.CUSTOM -> stringResource(R.string.filter_custom)
    }
}