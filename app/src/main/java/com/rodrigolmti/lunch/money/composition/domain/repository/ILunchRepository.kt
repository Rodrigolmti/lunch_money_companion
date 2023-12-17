package com.rodrigolmti.lunch.money.composition.domain.repository

import com.rodrigolmti.lunch.money.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome

internal interface ILunchRepository {
    suspend fun authenticateUser(tokenDTO: TokenDTO): Outcome<Unit, LunchError>
    suspend fun logoutUser(): Outcome<Unit, LunchError>
    suspend fun getTransactions(): Outcome<List<TransactionModel>, LunchError>
    fun getAssets(): List<AssetModel>
    suspend fun cacheTransactionCategories()
    suspend fun cacheAssets()
    fun getSessionUser(): UserModel?
    fun getSessionToken(): TokenDTO?
}