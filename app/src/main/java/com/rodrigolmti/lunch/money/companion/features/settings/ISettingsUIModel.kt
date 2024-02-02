package com.rodrigolmti.lunch.money.companion.features.settings

import kotlinx.coroutines.flow.StateFlow

internal interface ISettingsUIModel {
    val viewState: StateFlow<SettingsScreenUiState>

    fun updateCurrencyData(currency: String)

    fun getUserData()

    fun logout()
}