package com.rodrigolmti.lunch.money.companion.features.settings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.flow.MutableStateFlow

internal class DummyISettingsUIModel(state: SettingsScreenUiState = SettingsScreenUiState.Idle) : ISettingsUIModel {
    override val viewState = MutableStateFlow(state)

    override fun logout() {
        // no-op
    }
}

internal class SettingsUIModelProvider : PreviewParameterProvider<ISettingsUIModel> {
    override val values: Sequence<ISettingsUIModel>
        get() = sequenceOf(
            DummyISettingsUIModel(SettingsScreenUiState.Idle),
            DummyISettingsUIModel(SettingsScreenUiState.Logout),
            DummyISettingsUIModel(SettingsScreenUiState.Loading),
        )
}