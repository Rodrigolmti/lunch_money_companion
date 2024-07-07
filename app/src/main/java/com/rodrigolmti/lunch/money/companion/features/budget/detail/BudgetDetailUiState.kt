package com.rodrigolmti.lunch.money.companion.features.budget.detail

import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView

sealed class BudgetDetailUiState {
    data object Loading : BudgetDetailUiState()
    data object Error : BudgetDetailUiState()
    data class Success(val budget: BudgetView) : BudgetDetailUiState()
}