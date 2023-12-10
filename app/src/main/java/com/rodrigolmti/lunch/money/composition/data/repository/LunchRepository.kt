package com.rodrigolmti.lunch.money.composition.data.repository

import com.rodrigolmti.lunch.money.composition.data.mapper.toModel
import com.rodrigolmti.lunch.money.composition.data.mapper.toResponse
import com.rodrigolmti.lunch.money.composition.data.model.model.Token
import com.rodrigolmti.lunch.money.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.core.IDispatchersProvider
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.SharedPreferencesDelegateFactory
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching
import com.rodrigolmti.lunch.money.ui.features.authentication.data.model.UserModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException

internal interface ILunchRepository {
    suspend fun authenticateUser(token: Token): Outcome<UserModel, LunchError>
    suspend fun getTransactions(): Outcome<List<TransactionModel>, LunchError>
    suspend fun cacheTransactionCategories()
    suspend fun storeUser(userModel: UserModel)
    fun getSessionUser(): UserModel?
    fun getSessionToken(): Token?
}

internal class LunchRepository(
    private val json: Json,
    private val lunchService: LunchService,
    private val dispatchers: IDispatchersProvider,
    sharedPreferences: SharedPreferencesDelegateFactory,
) : ILunchRepository {

    private var user: String by sharedPreferences.create("", "user_key")
    private var token: String by sharedPreferences.create("", "token_key")

    private val transactionCategories: MutableList<TransactionCategoryModel> = mutableListOf()

    override suspend fun authenticateUser(token: Token): Outcome<UserModel, LunchError> {
        return withContext(dispatchers.io()) {
            runCatching {
                val user = lunchService.getUser(token.format()).toModel()
                this@LunchRepository.token = token.value
                user
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun getTransactions(): Outcome<List<TransactionModel>, LunchError> {
        return withContext(dispatchers.io()) {
            runCatching {
                val transactions = lunchService.getTransactions().transactions.map {
                    val category = transactionCategories.firstOrNull { category ->
                        category.id == it.categoryId
                    }
                    it.toModel(category)
                }
                transactions
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun cacheTransactionCategories() {
        withContext(dispatchers.io()) {
            runCatching {
                val categories = lunchService.getCategories().categories.map { it.toModel() }
                transactionCategories.clear()
                transactionCategories.addAll(categories)
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun storeUser(userModel: UserModel) {
        val response = userModel.toResponse()
        user = json.encodeToString(UserResponse.serializer(), response)
    }

    override fun getSessionUser(): UserModel? {
        return if (user.isNotEmpty()) {
            json.decodeFromString(UserResponse.serializer(), user).toModel()
        } else {
            null
        }
    }

    override fun getSessionToken(): Token? {
        if (token.isNotEmpty()) {
            return Token(token)
        }
        return null
    }

    private fun handleNetworkError(throwable: Throwable): LunchError {
        when (throwable) {
            is HttpException -> {
                return LunchError.NetworkError(
                    throwable = throwable,
                    message = throwable.message ?: "",
                    code = throwable.code(),
                )
            }

            else -> {
                return LunchError.NetworkError(
                    throwable = throwable,
                    message = throwable.message ?: "",
                    code = -1,
                )
            }
        }
    }
}
