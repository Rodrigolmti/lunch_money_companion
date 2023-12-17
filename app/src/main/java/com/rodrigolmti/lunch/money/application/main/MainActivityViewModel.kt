package com.rodrigolmti.lunch.money.application.main

import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.onFailure
import com.rodrigolmti.lunch.money.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private typealias LogoutUser = suspend () -> Outcome<Unit, LunchError>

class MainActivityViewModel(
    override val isUserAuthenticated: () -> Boolean,
    private val executeStartupLogic: suspend () -> Unit,
    private val logoutUser: LogoutUser
) : IMainActivityViewModel() {

    private val _viewState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    override val viewState: StateFlow<MainActivityUiState> = _viewState
    override fun logout() {
        viewModelScope.launch {
            _viewState.value = MainActivityUiState.Loading
            logoutUser().onSuccess {
                _viewState.value = MainActivityUiState.Logout
            }.onFailure {
                // no-op
            }
        }
    }

    init {
        viewModelScope.launch {
            if (!isUserAuthenticated()) {
                _viewState.value = MainActivityUiState.Finished
                return@launch
            }

            executeStartupLogic()
            _viewState.value = MainActivityUiState.Finished
        }
    }
}