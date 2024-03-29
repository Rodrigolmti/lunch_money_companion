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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private typealias UpdateCurrency = (String) -> Unit
private typealias LogoutUser = suspend () -> Outcome<Unit, LunchError>
private typealias GetUserData = suspend () -> Outcome<SettingsView, LunchError>

internal class SettingsViewModel(
    private val getUserDataRunner: GetUserData,
    private val logoutUserRunner: LogoutUser,
    private val updateCurrency: UpdateCurrency
) : ISettingsViewModel() {

    private val _viewState = MutableStateFlow<SettingsScreenUiState>(SettingsScreenUiState.Loading)
    override val viewState: StateFlow<SettingsScreenUiState> = _viewState

    override fun updateCurrencyData(currency: String) = updateCurrency(currency)

    override fun getUserData() {
        viewModelScope.launch {
            _viewState.update {
                SettingsScreenUiState.Loading
            }
            getUserDataRunner().onSuccess { result ->
                _viewState.update {
                    SettingsScreenUiState.Success(result)
                }
            }.onFailure {
                _viewState.update {
                    SettingsScreenUiState.Error
                }
            }
        }
    }

    override fun logout() {
        viewModelScope.launch {
            _viewState.update {
                SettingsScreenUiState.Loading
            }
            logoutUserRunner().onFinally {
                _viewState.update {
                    SettingsScreenUiState.Logout
                }
            }
        }
    }
}