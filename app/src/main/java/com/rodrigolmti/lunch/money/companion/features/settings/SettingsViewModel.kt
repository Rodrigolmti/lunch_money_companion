package com.rodrigolmti.lunch.money.companion.features.settings

import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onFinally
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private typealias LogoutUser = suspend () -> Outcome<Unit, LunchError>
private typealias GetUserData = suspend () -> Outcome<SettingsView, LunchError>

internal class SettingsViewModel(
    private val getUserDataRunner: GetUserData,
    private val logoutUserRunner: LogoutUser
) : ISettingsViewModel() {

    private val _viewState = MutableStateFlow<SettingsScreenUiState>(SettingsScreenUiState.Loading)
    override val viewState: StateFlow<SettingsScreenUiState> = _viewState
    override fun getUserData() {
        viewModelScope.launch {
            _viewState.value = SettingsScreenUiState.Loading
            getUserDataRunner().onSuccess {
                _viewState.value = SettingsScreenUiState.Success(it)
            }.onFailure {
                _viewState.value = SettingsScreenUiState.Error
            }
        }
    }

    override fun logout() {
        viewModelScope.launch {
            _viewState.value = SettingsScreenUiState.Loading
            logoutUserRunner().onFinally {
                _viewState.value = SettingsScreenUiState.Logout
            }
        }
    }
}