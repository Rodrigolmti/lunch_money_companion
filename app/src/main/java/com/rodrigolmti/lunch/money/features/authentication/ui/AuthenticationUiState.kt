package com.rodrigolmti.lunch.money.features.authentication.ui

sealed class AuthenticationUiState {
    data object Loading : AuthenticationUiState()
    data object Success : AuthenticationUiState()
    data object Error : AuthenticationUiState()
    data object Idle : AuthenticationUiState()

    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun isSuccess() = this is Success
}