package com.rodrigolmti.lunch.money.companion.features.navigation

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class BottomNavigationUiState {

    data class ShowErrorBottomSheet(val title: String, val message: String) :
        BottomNavigationUiState()

    data class ShowInformationBottomSheet(val title: String, val message: String) :
        BottomNavigationUiState()

    data object ShowDonationBottomSheet : BottomNavigationUiState()

    data object Idle : BottomNavigationUiState()
}