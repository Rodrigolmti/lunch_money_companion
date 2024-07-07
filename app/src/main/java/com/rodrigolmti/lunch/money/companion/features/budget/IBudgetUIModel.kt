package com.rodrigolmti.lunch.money.companion.features.budget

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal interface IBudgetUIModel {
    val viewState: StateFlow<BudgetUiState>

    fun getBudgetList(start: Date, end: Date)
}