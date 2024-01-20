package com.rodrigolmti.lunch.money.companion.composition.data.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.response.BudgetResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CategoryConfigResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CategoryResponse
import com.rodrigolmti.lunch.money.companion.composition.domain.model.Budget
import com.rodrigolmti.lunch.money.companion.composition.domain.model.Category
import com.rodrigolmti.lunch.money.companion.composition.domain.model.CategoryConfig

internal fun mapBudget(budgets: List<BudgetResponse>): List<Budget> {
    return budgets.map {
        it.toModel()
    }
}

internal fun BudgetResponse.toModel() = Budget(
    // TODO: Map to category model?
    categoryName = categoryName,
    categoryId = categoryId,
    categoryGroupName = categoryGroupName,
    groupId = groupId,
    isGroup = isGroup ?: false,
    isIncome = isIncome,
    excludeFromBudget = excludeFromBudget,
    excludeFromTotals = excludeFromTotals,
    data = data.map { it.value?.toModel(it.key) }.filterNotNull(),
    config = config?.toModel(),
    order = order,
)

internal fun CategoryResponse.toModel(date: String) = Category(
    numTransactions = numTransactions ?: 0,
    spendingToBase = spendingToBase ?: 0.0f,
    budgetToBase = budgetToBase ?: 0.0f,
    budgetAmount = budgetAmount ?: 0.0f,
    budgetCurrency = budgetCurrency,
    isAutomated = isAutomated ?: false,
    date = date,
)

internal fun CategoryConfigResponse.toModel() = CategoryConfig(
    configId = configId,
    cadence = cadence,
    amount = amount,
    currency = currency,
    toBase = toBase,
    autoSuggest = autoSuggest,
)