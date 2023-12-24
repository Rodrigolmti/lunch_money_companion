package com.rodrigolmti.lunch.money.features.settings

import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.onFinally
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private typealias LogoutUser = suspend () -> Outcome<Unit, LunchError>

class SettingsViewModel(
    private val logoutUser: LogoutUser
) : ISettingsViewModel() {

    private val _viewState = MutableStateFlow<SettingsScreenUiState>(SettingsScreenUiState.Idle)
    override val viewState: StateFlow<SettingsScreenUiState> = _viewState

    override fun logout() {
        viewModelScope.launch {
            _viewState.value = SettingsScreenUiState.Loading
            logoutUser().onFinally {
                _viewState.value = SettingsScreenUiState.Logout
                print("")
            }
        }
    }
}