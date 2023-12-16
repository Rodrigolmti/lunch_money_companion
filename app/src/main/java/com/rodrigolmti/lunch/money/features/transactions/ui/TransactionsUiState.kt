package com.rodrigolmti.lunch.money.features.transactions.ui

import com.rodrigolmti.lunch.money.features.transactions.model.TransactionView

sealed class TransactionsUiState {
    data object Loading : TransactionsUiState()
    data object Error : TransactionsUiState()
    data class Success(val transactions: List<TransactionView>) : TransactionsUiState()
}