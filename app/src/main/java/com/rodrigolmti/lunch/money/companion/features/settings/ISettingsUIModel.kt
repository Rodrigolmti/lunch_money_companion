package com.rodrigolmti.lunch.money.companion.features.settings

import kotlinx.coroutines.flow.StateFlow

interface ISettingsUIModel {
    val viewState: StateFlow<SettingsScreenUiState>

    fun logout()
}