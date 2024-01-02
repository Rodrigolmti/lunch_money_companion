package com.rodrigolmti.lunch.money.companion.features.navigation

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView

@Immutable
internal sealed class BottomNavigationUiState {
    data class ShowTransactionDetailBottomSheet(val transaction: TransactionView) :
        BottomNavigationUiState()

    data class ShowErrorBottomSheet(val title: String, val message: String) :
        BottomNavigationUiState()

    data class ShowInformationBottomSheet(val title: String, val message: String) :
        BottomNavigationUiState()

    data object Idle : BottomNavigationUiState()
}