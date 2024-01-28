package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetModel
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetItemView
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView

internal fun BudgetModel.toView(): BudgetView {
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