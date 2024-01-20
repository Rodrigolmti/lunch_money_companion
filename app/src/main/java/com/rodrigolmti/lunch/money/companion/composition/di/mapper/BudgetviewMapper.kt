package com.rodrigolmti.lunch.money.companion.composition.di.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.Budget
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetItemView
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView

internal fun Budget.toView(): BudgetView {
    return BudgetView(
        category = categoryName,
        items = data.map {
            BudgetItemView(
                totalTransactions = it.numTransactions,
                totalSpending = it.spendingToBase,
                totalBudget = it.budgetAmount,
                currency = it.budgetCurrency ?: "",
            )
        }
    )
}