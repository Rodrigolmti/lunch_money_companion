package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.flow.MutableStateFlow

internal class DummyAuthenticationUIModel(state: AuthenticationUiState = AuthenticationUiState.Idle) : IAuthenticationUIModel {
    override val viewState = MutableStateFlow(state)
    override fun onGetStartedClicked(token: String) {
        // no-op
    }
}

internal class AuthenticationUIModelProvider : PreviewParameterProvider<IAuthenticationUIModel> {
    override val values: Sequence<IAuthenticationUIModel>
        get() = sequenceOf(
            DummyAuthenticationUIModel(AuthenticationUiState.Idle),
            DummyAuthenticationUIModel(AuthenticationUiState.Loading),
            DummyAuthenticationUIModel(AuthenticationUiState.Error),
            DummyAuthenticationUIModel(AuthenticationUiState.Success),
        )
}