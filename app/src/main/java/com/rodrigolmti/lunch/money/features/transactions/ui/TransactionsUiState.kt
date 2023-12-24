package com.rodrigolmti.lunch.money.features.transactions.ui

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.features.transactions.model.TransactionView
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed class TransactionsUiState {
    @Immutable
    data object Loading : TransactionsUiState()

    @Immutable
    data object Error : TransactionsUiState()

    @Immutable
    data class Success(val transactions: ImmutableList<TransactionView>) : TransactionsUiState()
}