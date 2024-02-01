package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

@Immutable
data class TransactionSummaryView(
    val income: Map<String, Float>,
    val totalIncome: Float,
    val expense: Map<String, Float>,
    val totalExpense: Float,
    val net: Float,
    val currency: String
)

fun fakeTransactionSummaryView() = TransactionSummaryView(
    income = mapOf(
        "salary" to 100f,
        "dividends" to 200f,
        "interests" to 300f,
    ),
    totalIncome = 600f,
    expense = mapOf(
        "groceries" to 50f,
        "subscriptions" to 100f,
        "rent" to 150f,
    ),
    totalExpense = 300f,
    net = 300f,
    currency = ValueGenerator.currency()
)