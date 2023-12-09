package com.rodrigolmti.lunch.money.ui.features.start.ui

sealed class AuthenticationUiState {
    data object Loading : AuthenticationUiState()
    data object Success : AuthenticationUiState()
    data object Error : AuthenticationUiState()
    data object Idle : AuthenticationUiState()

    fun isLoading() = this is Loading
    fun isError() = this is Error
}