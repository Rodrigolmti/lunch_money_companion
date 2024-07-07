package com.rodrigolmti.lunch.money.companion.composition.data.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.response.BudgetRecurringResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.BudgetResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CategoryConfigResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CategoryResponse
import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetRecurringModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.CategoryConfigModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.CategoryModel

internal fun mapBudget(budgets: List<BudgetResponse>): List<BudgetModel> {
    return budgets.map {
        it.toModel()
    }
}

internal fun BudgetResponse.toModel(): BudgetModel {
    val data: Map<String, CategoryModel> = data
        .filter { it.value != null }
        .mapValues { it.value!!.toModel() }

    return BudgetModel(
        categoryName = categoryName,
        categoryId = categoryId,
        categoryGroupName = categoryGroupName,
        groupId = groupId,
        isGroup = isGroup ?: false,
        isIncome = isIncome,
        excludeFromBudget = excludeFromBudget,
        excludeFromTotals = excludeFromTotals,
        data = data,
        config = config?.toModel(),
        order = order,
        recurring = recurring?.list?.map { it.toModel() } ?: emptyList(),
    )
}

internal fun BudgetRecurringResponse.toModel() = BudgetRecurringModel(
    payee = payee,
    amount = amount,
    currency = currency,
)

internal fun CategoryResponse.toModel() = CategoryModel(
    numTransactions = numTransactions ?: 0,
    spendingToBase = spendingToBase ?: 0.0f,
    budgetToBase = budgetToBase ?: 0.0f,
    budgetAmount = budgetAmount ?: 0.0f,
    budgetCurrency = budgetCurrency,
    isAutomated = isAutomated ?: false,
)

internal fun CategoryConfigResponse.toModel() = CategoryConfigModel(
    configId = configId,
    cadence = cadence,
    amount = amount,
    currency = currency,
    toBase = toBase,
    autoSuggest = autoSuggest,
)