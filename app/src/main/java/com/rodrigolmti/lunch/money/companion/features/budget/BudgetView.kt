package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator
import java.util.UUID

@Immutable
data class BudgetView(
    val category: String,
    val id: Int?,
    val uuid: UUID = UUID.randomUUID(),
    val items: Map<String, BudgetItemView>,
)

@Immutable
data class BudgetItemView(
    val totalTransactions: Int,
    val totalSpending: Float,
    val totalBudget: Float,
    val currency: String
)

fun fakeBudgetView(
    items: Map<String, BudgetItemView> = mapOf(
        ValueGenerator.gen<String>() to fakeBudgetItemView(),
        ValueGenerator.gen<String>() to fakeBudgetItemView(),
        ValueGenerator.gen<String>() to fakeBudgetItemView(),
    ),
) = BudgetView(
    category = ValueGenerator.gen(),
    id = ValueGenerator.gen(),
    items = items,
)

fun fakeBudgetItemView() = BudgetItemView(
    totalTransactions = ValueGenerator.gen(),
    totalSpending = ValueGenerator.gen(),
    totalBudget = ValueGenerator.gen(),
    currency = ValueGenerator.currency(),
)