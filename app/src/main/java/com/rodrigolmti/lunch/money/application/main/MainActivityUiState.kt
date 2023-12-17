package com.rodrigolmti.lunch.money.application.main

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()
    data object Finished : MainActivityUiState()
    data object Logout : MainActivityUiState()
}