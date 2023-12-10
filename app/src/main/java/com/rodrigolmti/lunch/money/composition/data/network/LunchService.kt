package com.rodrigolmti.lunch.money.composition.data.network

import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionBodyResponse
import com.rodrigolmti.lunch.money.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.core.network.Authenticated
import retrofit2.http.GET
import retrofit2.http.Header

interface LunchService {

    @GET("v1/me")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse

    @Authenticated
    @GET("v1/transactions")
    suspend fun getTransactions(): TransactionBodyResponse
}