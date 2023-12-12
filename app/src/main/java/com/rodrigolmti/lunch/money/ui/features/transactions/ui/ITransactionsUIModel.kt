package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import kotlinx.coroutines.flow.StateFlow

interface ITransactionsUIModel {
    val viewState: StateFlow<TransactionsUiState>

    fun getTransactions()
}