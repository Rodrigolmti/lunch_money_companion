package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import com.rodrigolmti.lunch.money.companion.features.transactions.model.UpdateTransactionView
import kotlinx.coroutines.flow.StateFlow

internal interface ITransactionDetailUIModel {
    val viewState: StateFlow<TransactionDetailUiState>

    fun getTransaction(id: Int)

    fun updateTransaction(model: UpdateTransactionView)
}