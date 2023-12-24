package com.rodrigolmti.lunch.money.companion.application.main

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainActivityUiState {
    @Immutable
    data object Loading : MainActivityUiState()

    @Immutable
    data object Finished : MainActivityUiState()
}