package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import kotlinx.coroutines.flow.StateFlow

internal interface ITransactionsUIModel {
    val viewState: StateFlow<TransactionsUiState>

    fun getTransactions()
}