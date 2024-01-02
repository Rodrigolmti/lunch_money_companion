package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal interface ITransactionsUIModel {
    val viewState: StateFlow<TransactionsUiState>

    fun getTransactions(start: Date, end: Date)
}