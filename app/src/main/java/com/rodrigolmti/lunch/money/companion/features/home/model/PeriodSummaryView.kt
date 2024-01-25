package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

@Immutable
internal data class PeriodSummaryView(
    val totalIncome: Float,
    val totalExpense: Float,
    val netIncome: Float,
    val currency: String,
    val savingsRate: Int,
)

internal fun fakePeriodSummaryView() = PeriodSummaryView(
    totalIncome = ValueGenerator.gen(),
    totalExpense = ValueGenerator.gen(),
    netIncome = ValueGenerator.gen(),
    currency = ValueGenerator.currency(),
    savingsRate = ValueGenerator.gen(),
)