package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel

sealed class TransactionsUiState {
    data object Idle : TransactionsUiState()
    data object Loading : TransactionsUiState()
    data object Error : TransactionsUiState()
    data class Success(val transactions: List<TransactionModel>) : TransactionsUiState()
}