package com.rodrigolmti.lunch.money.companion.composition.domain.model

data class Budget(
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
    val order: Int
)

data class Category(
    val numTransactions: Int,
    val spendingToBase: Float,
    val budgetToBase: Float,
    val budgetAmount: Float,
    val budgetCurrency: String?,
    val isAutomated: Boolean,
    val date: String
)

data class CategoryConfig(
    val configId: Int,
    val cadence: String,
    val amount: Float,
    val currency: String,
    val toBase: Float,
    val autoSuggest: String
)