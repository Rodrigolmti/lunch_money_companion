package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.CategoryModel
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetItemView
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView

internal fun BudgetModel.toView(currency: String): BudgetView {
    return BudgetView(
        category = categoryName,
        id = categoryId,
        items = data.mapValues {
            it.value.toView(currency)
        },
    )
}

internal fun CategoryModel.toView(currency: String): BudgetItemView {
    return BudgetItemView(
        totalTransactions = numTransactions,
        totalSpending = spendingToBase,
        totalBudget = budgetToBase,
        currency = currency,
    )
}