package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import androidx.compose.runtime.Immutable

@Immutable
sealed class AuthenticationUiState {
    @Immutable
    data object Loading : AuthenticationUiState()

    @Immutable
    data object Success : AuthenticationUiState()

    @Immutable
    data object Error : AuthenticationUiState()

    @Immutable
    data object Idle : AuthenticationUiState()

    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun isSuccess() = this is Success
}