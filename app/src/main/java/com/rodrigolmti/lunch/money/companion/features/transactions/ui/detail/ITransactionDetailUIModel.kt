package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionDetailView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.UpdateTransactionView
import kotlinx.coroutines.flow.StateFlow

internal interface ITransactionDetailUIModel {
    val viewState: StateFlow<TransactionDetailUiState>

    fun getTransaction(id: Long)

    fun updateTransaction(
        update: UpdateTransactionView,
        model: TransactionDetailView,
    )
}