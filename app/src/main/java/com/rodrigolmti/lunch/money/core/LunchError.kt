package com.rodrigolmti.lunch.money.core

sealed class LunchError {
    data class NetworkError(
        val throwable: Throwable,
        val message: String,
        val code: Int,
    ) : LunchError()
}