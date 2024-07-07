package com.rodrigolmti.lunch.money.companion.composition.domain.repository

import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionDTO
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.RecurringModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import kotlinx.coroutines.flow.SharedFlow

internal interface IAppRepository {
    suspend fun authenticateUser(tokenDTO: TokenDTO): Outcome<Unit, LunchError>
    suspend fun logoutUser(): Outcome<Unit, LunchError>
    suspend fun getTransactions(
        start: String,
        end: String,
    ): Outcome<List<TransactionModel>, LunchError>

    suspend fun getRecurring(): Outcome<List<RecurringModel>, LunchError>
    suspend fun getTransaction(id: Long): Outcome<TransactionModel, LunchError>
    suspend fun updateTransaction(dto: UpdateTransactionDTO): Outcome<Unit, LunchError>

    fun getBudgetById(id: Int): BudgetModel?
    suspend fun getBudgets(
        start: String,
        end: String,
    ): Outcome<List<BudgetModel>, LunchError>

    fun getAssets(): List<AssetModel>
    suspend fun cacheTransactionCategories()
    suspend fun cacheAssets()
    fun getSessionUser(): UserModel?
    fun getSessionToken(): TokenDTO?

    fun updatePrimaryCurrency(currency: String)
    fun getPrimaryCurrency(): String?
    fun getCategories(): List<TransactionCategoryModel>

    val transactionUpdateFlow: SharedFlow<Unit>
}