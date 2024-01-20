package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.runtime.Immutable
import java.util.Date
import java.util.UUID

@Immutable
data class BudgetView(
    val category: String,
    val uuid: UUID = UUID.randomUUID(),
    val items: List<BudgetItemView>,
)

@Immutable
data class BudgetItemView(
    val totalTransactions: Int,
    val totalSpending: Float,
    val totalBudget: Float,
    val currency: String
)

fun fakeBudgetView() = BudgetView(
    category = "Household",
    items = listOf(
        BudgetItemView(
            totalTransactions = 1,
            totalSpending = 1.0f,
            totalBudget = 1.0f,
            currency = "EUR"
        ),
        BudgetItemView(
            totalTransactions = 2,
            totalSpending = 2.0f,
            totalBudget = 2.0f,
            currency = "EUR"
        ),
        BudgetItemView(
            totalTransactions = 3,
            totalSpending = 3.0f,
            totalBudget = 3.0f,
            currency = "EUR"
        ),
    ),
)