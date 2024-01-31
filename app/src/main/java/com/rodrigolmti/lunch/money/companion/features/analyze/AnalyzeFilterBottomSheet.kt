@file:OptIn(ExperimentalLayoutApi::class)

package com.rodrigolmti.lunch.money.companion.features.analyze

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import java.util.Date

import java.util.*

enum class AnalyzeFilterPreset {
    MONTH_TO_DATE,
    YEAR_TO_DATE,
    LAST_7_DAYS,
    LAST_30_DAYS,
    LAST_MONTH,
    LAST_3_MONTHS,
}

fun AnalyzeFilterPreset.mapFilterPreset(): Pair<Date, Date> {
    val end = Date()
    val calendar = Calendar.getInstance()
    calendar.time = end

    return when (this) {
        AnalyzeFilterPreset.MONTH_TO_DATE -> {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val start = calendar.time
            start to end
        }
        AnalyzeFilterPreset.YEAR_TO_DATE -> {
            calendar.set(Calendar.DAY_OF_YEAR, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val start = calendar.time
            start to end
        }
        AnalyzeFilterPreset.LAST_7_DAYS -> {
            calendar.add(Calendar.DAY_OF_YEAR, -6)
            val start = calendar.time
            start to end
        }
        AnalyzeFilterPreset.LAST_30_DAYS -> {
            calendar.add(Calendar.DAY_OF_YEAR, -29)
            val start = calendar.time
            start to end
        }
        AnalyzeFilterPreset.LAST_MONTH -> {
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val start = calendar.time
            start to end
        }
        AnalyzeFilterPreset.LAST_3_MONTHS -> {
            calendar.add(Calendar.MONTH, -2)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val start = calendar.time
            start to end
        }
    }
}

@Composable
@LunchMoneyPreview
fun AnalyzeFilterBottomSheetPreview() {
    CompanionTheme {
        AnalyzeFilterBottomSheet(
            selected = AnalyzeFilterPreset.LAST_7_DAYS,
            onFilterSelected = {},
            onFilter = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyzeFilterBottomSheet(
    selected: AnalyzeFilterPreset,
    onFilterSelected: (AnalyzeFilterPreset) -> Unit,
    onFilter: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(CompanionTheme.spacings.spacingD),
    ) {
        Text(
            "Filter",
            style = CompanionTheme.typography.header,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
        Text(
            "Select the date range to filter the data",
            style = CompanionTheme.typography.body,
            color = White,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingE)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingA)
        ) {
            AnalyzeFilterPreset.entries.forEach {
                FilterChip(
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

        VerticalSpacer(height = CompanionTheme.spacings.spacingE)

        LunchButton(
            label = "Filter",
        ) {
            onFilter()
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingE)
    }
}

private fun getLabelByPreset(preset: AnalyzeFilterPreset): String {
    return when (preset) {
        AnalyzeFilterPreset.MONTH_TO_DATE -> "Month to date"
        AnalyzeFilterPreset.YEAR_TO_DATE -> "Year to date"
        AnalyzeFilterPreset.LAST_7_DAYS -> "Last 7 days"
        AnalyzeFilterPreset.LAST_30_DAYS -> "Last 30 days"
        AnalyzeFilterPreset.LAST_MONTH -> "Last month"
        AnalyzeFilterPreset.LAST_3_MONTHS -> "Last 3 months"
    }
}