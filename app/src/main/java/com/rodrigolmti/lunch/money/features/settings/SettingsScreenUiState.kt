package com.rodrigolmti.lunch.money.features.settings

sealed class SettingsScreenUiState {
    data object Loading : SettingsScreenUiState()
    data object Idle : SettingsScreenUiState()
    data object Logout : SettingsScreenUiState()
}