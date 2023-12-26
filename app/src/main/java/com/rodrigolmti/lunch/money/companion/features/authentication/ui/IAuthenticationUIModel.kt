package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import kotlinx.coroutines.flow.StateFlow

internal interface IAuthenticationUIModel {
    val viewState: StateFlow<AuthenticationUiState>

    fun onGetStartedClicked(token: String)
}