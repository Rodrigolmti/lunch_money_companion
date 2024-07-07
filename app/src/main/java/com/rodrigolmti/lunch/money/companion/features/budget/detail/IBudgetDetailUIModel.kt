package com.rodrigolmti.lunch.money.companion.features.budget.detail

import kotlinx.coroutines.flow.StateFlow

/**
 * Add a component to control which month the budget should be set to;
 * Add a list of recurring items related to this budget on a horizontal list;
 * Add a list of months that contains this budget on a horizontal list;
 *  With budget value and total spending;
 * Remove from the list of budgets the fields budget value and total spending;
 * Cache the budget item on the repository, so the detail screen have access to it;
 */

internal interface IBudgetDetailUIModel {
    val viewState: StateFlow<BudgetDetailUiState>

    fun getBudget(budgetId: Int)
}