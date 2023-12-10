package com.rodrigolmti.lunch.money.ui.features.start.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.onFailure
import com.rodrigolmti.lunch.money.core.onSuccess
import com.rodrigolmti.lunch.money.ui.features.start.data.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class IAuthenticationViewModel : ViewModel(), IAuthenticationUIModel

class AuthenticationViewModel(
    private val authenticateUser: suspend (String) -> Outcome<UserModel, LunchError>,
    private val storeUser: suspend (UserModel) -> Unit,
) : IAuthenticationViewModel() {
    private val _viewState = MutableStateFlow<AuthenticationUiState>(AuthenticationUiState.Idle)
    override val viewState: StateFlow<AuthenticationUiState> = _viewState

    override fun onGetStartedClicked(token: String) {
        viewModelScope.launch {
            _viewState.value = AuthenticationUiState.Loading
            authenticateUser(token)
                .onSuccess {
                    storeUser(it)
                    _viewState.value = AuthenticationUiState.Success
                }
                .onFailure {
                    _viewState.value = AuthenticationUiState.Error
                }
        }
    }
}

