package com.rodrigolmti.lunch.money.companion.features.settings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView
import com.rodrigolmti.lunch.money.companion.features.settings.model.fakeSettingsView
import kotlinx.coroutines.flow.MutableStateFlow

internal class DummyISettingsUIModel(state: SettingsScreenUiState = SettingsScreenUiState.Loading) :
    ISettingsUIModel {
    override val viewState = MutableStateFlow(state)

    override fun updateCurrencyData(currency: String) {
        // no-op
    }

    override fun getUserData() {
        // no-op
    }

    override fun logout() {
        // no-op
    }
}

internal class SettingsUIModelProvider : PreviewParameterProvider<ISettingsUIModel> {
    override val values: Sequence<ISettingsUIModel>
        get() = sequenceOf(
            DummyISettingsUIModel(SettingsScreenUiState.Logout),
            DummyISettingsUIModel(SettingsScreenUiState.Loading),
            DummyISettingsUIModel(
                SettingsScreenUiState.Success(
                    fakeSettingsView()
                )
            ),
        )
}