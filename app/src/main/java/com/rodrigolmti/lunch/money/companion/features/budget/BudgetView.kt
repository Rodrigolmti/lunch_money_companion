package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator
import java.util.UUID

@Immutable
data class BudgetView(
    val category: String,
    val uuid: UUID = UUID.randomUUID(),
    val items: List<BudgetItemView>,
    val recurring: List<RecurringView> = emptyList(),
)

@Immutable
data class BudgetItemView(
    val totalTransactions: Int,
    val totalSpending: Float,
    val totalBudget: Float,
    val currency: String
)

@Immutable
data class RecurringView(
    val payee: String,
    val amount: Float,
    val currency: String,
)

fun fakeBudgetView(
    recurring: List<RecurringView> = listOf(
        RecurringView(
            payee = ValueGenerator.gen(),
            amount = ValueGenerator.gen(),
            currency = ValueGenerator.currency(),
        ),
        RecurringView(
            payee = ValueGenerator.gen(),
            amount = ValueGenerator.gen(),
            currency = ValueGenerator.currency(),
        ),
        RecurringView(
            payee = ValueGenerator.gen(),
            amount = ValueGenerator.gen(),
            currency = ValueGenerator.currency(),
        ),
    ),
    items: List<BudgetItemView> = listOf(
        BudgetItemView(
            totalTransactions = ValueGenerator.gen(),
            totalSpending = ValueGenerator.gen(),
            totalBudget = ValueGenerator.gen(),
            currency = ValueGenerator.currency(),
        ),
        BudgetItemView(
            totalTransactions = ValueGenerator.gen(),
            totalSpending = ValueGenerator.gen(),
            totalBudget = ValueGenerator.gen(),
            currency = ValueGenerator.currency(),
        ),
        BudgetItemView(
            totalTransactions = ValueGenerator.gen(),
            totalSpending = ValueGenerator.gen(),
            totalBudget = ValueGenerator.gen(),
            currency = ValueGenerator.currency(),
        ),
    ),
) = BudgetView(
    category = ValueGenerator.gen(),
    recurring = recurring,
    items = items,
)