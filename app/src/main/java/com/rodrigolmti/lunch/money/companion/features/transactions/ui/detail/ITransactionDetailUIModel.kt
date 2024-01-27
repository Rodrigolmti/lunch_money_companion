package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import kotlinx.coroutines.flow.StateFlow

internal interface ITransactionDetailUIModel {
    val viewState: StateFlow<TransactionDetailUiState>

    fun getTransaction(id: Int)
}