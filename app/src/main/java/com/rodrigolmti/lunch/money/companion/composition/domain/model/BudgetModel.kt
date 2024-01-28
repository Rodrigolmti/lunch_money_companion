package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal data class BudgetModel(
    val categoryName: String,
    val categoryId: Int?,
    val categoryGroupName: String?,
    val groupId: Int?,
    val isGroup: Boolean,
    val isIncome: Boolean,
    val excludeFromBudget: Boolean,
    val excludeFromTotals: Boolean,
    val data: List<CategoryModel>,
    val config: CategoryConfigModel?,
    val order: Int,
    val recurring: List<BudgetRecurringModel> = emptyList()
)

internal data class BudgetRecurringModel(
    val payee: String,
    val amount: Float,
    val currency: String,
)

internal data class CategoryModel(
    val numTransactions: Int,
    val spendingToBase: Float,
    val budgetToBase: Float,
    val budgetAmount: Float,
    val budgetCurrency: String?,
    val isAutomated: Boolean,
    val date: String
)

internal data class CategoryConfigModel(
    val configId: Int,
    val cadence: String,
    val amount: Float,
    val currency: String,
    val toBase: Float,
    val autoSuggest: String
)