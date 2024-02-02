package com.rodrigolmti.lunch.money.companion.composition.data.network

import com.rodrigolmti.lunch.money.companion.composition.data.model.response.AssetsBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.BudgetResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CryptoBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.PlaidAccountBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.RecurringBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionCategoryBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UpdateTransactionResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.companion.core.network.Authenticated
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

internal interface LunchService {

    @GET("v1/me")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse

    @Authenticated
    @GET("v1/transactions")
    suspend fun getTransactions(
        @Query("start_date") start: String,
        @Query("end_date") end: String,
    ): TransactionBodyResponse

    @Authenticated
    @GET("v1/recurring_expenses")
    suspend fun getRecurring(): RecurringBodyResponse

    @Authenticated
    @GET("v1/transactions/{id}")
    suspend fun getTransaction(
        @Path("id") id: Int,
    ): TransactionResponse

    @Authenticated
    @PUT("v1/transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body transaction: UpdateTransactionResponse,
    ): TransactionResponse

    @Authenticated
    @GET("v1/categories")
    suspend fun getCategories(): TransactionCategoryBodyResponse

    @Authenticated
    @GET("v1/assets")
    suspend fun getAssets(): AssetsBodyResponse

    @Authenticated
    @GET("v1/plaid_accounts")
    suspend fun getPlaidAccounts(): PlaidAccountBodyResponse

    @Authenticated
    @GET("v1/crypto")
    suspend fun getCrypto(): CryptoBodyResponse

    @Authenticated
    @GET("v1/budgets")
    suspend fun getBudgets(
        @Query("start_date") start: String,
        @Query("end_date") end: String,
    ): List<BudgetResponse>
}