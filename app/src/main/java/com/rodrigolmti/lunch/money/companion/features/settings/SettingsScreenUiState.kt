package com.rodrigolmti.lunch.money.companion.features.settings

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView

@Immutable
internal sealed class SettingsScreenUiState {
    data object Loading : SettingsScreenUiState()
    data object Logout : SettingsScreenUiState()
    data object Error : SettingsScreenUiState()
    data class Success(val data: SettingsView): SettingsScreenUiState()
}