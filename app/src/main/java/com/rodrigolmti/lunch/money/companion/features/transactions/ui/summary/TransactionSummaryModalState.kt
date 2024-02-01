package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

sealed class TransactionSummaryModalState {
    data object ErrorModal : TransactionSummaryModalState()
    data object FilterModal : TransactionSummaryModalState()
    data object Idle : TransactionSummaryModalState()
}