package com.rodrigolmti.lunch.money.companion.features.settings

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class SettingsScreenUiState {
    data object Loading : SettingsScreenUiState()
    data object Idle : SettingsScreenUiState()
    data object Logout : SettingsScreenUiState()
}