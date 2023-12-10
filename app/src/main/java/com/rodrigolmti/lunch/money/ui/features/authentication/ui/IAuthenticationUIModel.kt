package com.rodrigolmti.lunch.money.ui.features.authentication.ui

import kotlinx.coroutines.flow.StateFlow

interface IAuthenticationUIModel {
    val viewState: StateFlow<AuthenticationUiState>

    fun onGetStartedClicked(token: String)
}