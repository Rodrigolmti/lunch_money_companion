package com.rodrigolmti.lunch.money.companion.features.authentication.ui

sealed class AuthenticationScreenErrorState {
    data object NoConnectionError : AuthenticationScreenErrorState()
    data object InvalidTokenError : AuthenticationScreenErrorState()
    data object Idle : AuthenticationScreenErrorState()
}