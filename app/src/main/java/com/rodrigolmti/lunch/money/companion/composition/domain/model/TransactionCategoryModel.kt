package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal data class TransactionCategoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    val isIncome: Boolean,
    val excludeFromBudget: Boolean,
    val excludeFromTotals: Boolean,
)