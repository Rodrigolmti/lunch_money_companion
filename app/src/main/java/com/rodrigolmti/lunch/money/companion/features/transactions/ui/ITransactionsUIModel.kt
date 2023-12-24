package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import kotlinx.coroutines.flow.StateFlow

interface ITransactionsUIModel {
    val viewState: StateFlow<TransactionsUiState>

    fun getTransactions()
}