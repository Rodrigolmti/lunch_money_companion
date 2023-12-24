package com.rodrigolmti.lunch.money.companion.application.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class IMainActivityViewModel : ViewModel() {
    abstract val viewState: StateFlow<MainActivityUiState>

    abstract val isUserAuthenticated: () -> Boolean
}