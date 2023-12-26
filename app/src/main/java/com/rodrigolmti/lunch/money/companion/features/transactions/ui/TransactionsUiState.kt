package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal sealed class TransactionsUiState {
    data object Loading : TransactionsUiState()

    data object Error : TransactionsUiState()

    data class Success(val transactions: ImmutableList<TransactionView>) : TransactionsUiState()
}