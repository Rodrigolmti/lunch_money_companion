package com.rodrigolmti.lunch.money.companion.features.budget

import kotlinx.collections.immutable.ImmutableList

sealed class BudgetUiState {
    data object Loading : BudgetUiState()
    data object Error : BudgetUiState()
    data class Success(val view: ImmutableList<BudgetView>) : BudgetUiState()
}