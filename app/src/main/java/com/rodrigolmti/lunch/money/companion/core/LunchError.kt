package com.rodrigolmti.lunch.money.companion.core

sealed class LunchError {
    data object EmptyDataError : LunchError()
    data object UnknownError : LunchError()
    data object InvalidDataError : LunchError()
    data object UnsuccessfulLogoutError : LunchError()
    data object NoConnectionError : LunchError()
    data class NetworkError(
        val throwable: Throwable,
        val message: String,
        val code: Int,
    ) : LunchError()
}

fun LunchError.isNoConnectionError(): Boolean = this is LunchError.NoConnectionError