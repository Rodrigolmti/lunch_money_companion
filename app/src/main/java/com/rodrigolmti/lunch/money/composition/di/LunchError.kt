package com.rodrigolmti.lunch.money.composition.di

sealed class LunchError {
    data class NetworkError(
        val throwable: Throwable,
        val message: String,
        val code: Int,
    ) : LunchError()
    data object UnknownError : LunchError()
}