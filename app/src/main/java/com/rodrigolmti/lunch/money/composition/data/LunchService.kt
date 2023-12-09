package com.rodrigolmti.lunch.money.composition.data

import com.rodrigolmti.lunch.money.composition.data.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface LunchService {

    @GET("v1/me")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse
}