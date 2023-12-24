package com.rodrigolmti.lunch.money.application.main

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainActivityUiState {
    @Immutable
    data object Loading : MainActivityUiState()

    @Immutable
    data object Finished : MainActivityUiState()
}