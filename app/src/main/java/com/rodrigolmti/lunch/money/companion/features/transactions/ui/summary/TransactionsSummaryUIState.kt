package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class TransactionsSummaryUIState {
    data object Loading : TransactionsSummaryUIState()

    data object Error : TransactionsSummaryUIState()

    data class Success(val view: TransactionSummaryView) : TransactionsSummaryUIState()
}