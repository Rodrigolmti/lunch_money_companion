package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal data class Budget(
    val categoryName: String,
    val categoryId: Int?,
    val categoryGroupName: String?,
    val groupId: Int?,
    val isGroup: Boolean,
    val isIncome: Boolean,
    val excludeFromBudget: Boolean,
    val excludeFromTotals: Boolean,
    val data: List<Category>,
    val config: CategoryConfig?,
    val order: Int,
    val recurring: List<BudgetRecurring> = emptyList()
)

internal data class BudgetRecurring(
    val payee: String,
    val amount: Float,
    val currency: String,
)

internal data class Category(
    val numTransactions: Int,
    val spendingToBase: Float,
    val budgetToBase: Float,
    val budgetAmount: Float,
    val budgetCurrency: String?,
    val isAutomated: Boolean,
    val date: String
)

internal data class CategoryConfig(
    val configId: Int,
    val cadence: String,
    val amount: Float,
    val currency: String,
    val toBase: Float,
    val autoSuggest: String
)