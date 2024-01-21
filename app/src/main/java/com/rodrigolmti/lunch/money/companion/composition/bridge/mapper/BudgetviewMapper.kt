package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.Budget
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetItemView
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView
import com.rodrigolmti.lunch.money.companion.features.budget.RecurringView

internal fun Budget.toView(): BudgetView {
    return BudgetView(
        category = categoryName,
        recurring = recurring.map {
            RecurringView(
                payee = it.payee,
                amount = it.amount,
                currency = it.currency,
            )
        },
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