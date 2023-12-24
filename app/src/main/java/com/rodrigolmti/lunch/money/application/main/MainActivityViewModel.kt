package com.rodrigolmti.lunch.money.application.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    override val isUserAuthenticated: () -> Boolean,
    private val executeStartupLogic: suspend () -> Unit,
) : IMainActivityViewModel() {

    private val _viewState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    override val viewState: StateFlow<MainActivityUiState> = _viewState

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