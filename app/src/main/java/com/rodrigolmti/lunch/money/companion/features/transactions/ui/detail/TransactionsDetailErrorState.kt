package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

sealed class TransactionsDetailErrorState {
    data object Idle : TransactionsDetailErrorState()
    data object GetTransactionError : TransactionsDetailErrorState()
}