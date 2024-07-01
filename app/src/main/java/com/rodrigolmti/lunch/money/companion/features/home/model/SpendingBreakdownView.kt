package com.rodrigolmti.lunch.money.companion.features.home.model

internal data class SpendingBreakdownView(
    val incomes: List<SpendingItemView>,
    val expenses: List<SpendingItemView>,
)

internal data class SpendingItemView(
    val name: String,
    val percentage: Int,
    val value: Float,
    val currency: String,
)

internal fun fakeSpendingBreakdownView() = SpendingBreakdownView(
    incomes = (0..3).map {
        SpendingItemView(
            name = "Income asdf $it",
            percentage = (0..100).random(),
            value = (0..1000).random().toFloat(),
            currency = "USD",
        )
    },
    expenses = (0..3).map {
        SpendingItemView(
            name = "Expense $it",
            percentage = (0..100).random(),
            value = (0..1000).random().toFloat(),
            currency = "USD",
        )
    }
)