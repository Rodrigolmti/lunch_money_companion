package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class AuthenticationUiState {
    data object Loading : AuthenticationUiState()

    data object Success : AuthenticationUiState()

    data object Error : AuthenticationUiState()

    data object Idle : AuthenticationUiState()

    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun isSuccess() = this is Success
}