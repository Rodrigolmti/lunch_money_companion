package com.rodrigolmti.lunch.money.companion.application.main

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()
    data object Finished : MainActivityUiState()
}