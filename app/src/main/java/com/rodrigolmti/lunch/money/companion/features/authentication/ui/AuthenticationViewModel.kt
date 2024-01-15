package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal abstract class IAuthenticationViewModel : ViewModel(), IAuthenticationUIModel

internal class AuthenticationViewModel(
    private val authenticateUser: suspend (String) -> Outcome<Unit, LunchError>,
    private val postAuthentication: suspend () -> Unit,
) : IAuthenticationViewModel() {
    private val _viewState = MutableStateFlow<AuthenticationUiState>(AuthenticationUiState.Idle)
    override val viewState: StateFlow<AuthenticationUiState> = _viewState

    override fun onGetStartedClicked(token: String) {
        viewModelScope.launch {
            _viewState.update { AuthenticationUiState.Loading }
            authenticateUser(token)
                .onSuccess {
                    postAuthentication()
                    _viewState.update { AuthenticationUiState.Success }
                }
                .onFailure {
                    _viewState.update { AuthenticationUiState.Error }
                }
        }
    }
}

