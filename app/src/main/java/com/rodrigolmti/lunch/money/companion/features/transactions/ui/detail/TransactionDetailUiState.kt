package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView

@Immutable
internal sealed class TransactionDetailUiState {
    data object Loading : TransactionDetailUiState()

    data object Error : TransactionDetailUiState()

    data class Success(val transaction: TransactionView) : TransactionDetailUiState()
}