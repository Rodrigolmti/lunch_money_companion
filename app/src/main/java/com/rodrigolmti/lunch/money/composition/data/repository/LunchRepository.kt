package com.rodrigolmti.lunch.money.composition.data.repository

import com.rodrigolmti.lunch.money.composition.data.mapper.mapTransactions
import com.rodrigolmti.lunch.money.composition.data.mapper.toModel
import com.rodrigolmti.lunch.money.composition.data.mapper.toResponse
import com.rodrigolmti.lunch.money.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.core.IDispatchersProvider
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.SharedPreferencesDelegateFactory
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException

internal class LunchRepository(
    private val json: Json,
    private val lunchService: LunchService,
    private val dispatchers: IDispatchersProvider,
    preferences: SharedPreferencesDelegateFactory,
) : ILunchRepository {

    private var user: String by preferences.create("", "user_key")
    private var token: String by preferences.create("", "token_key")

    private val cachedCategories: MutableList<TransactionCategoryModel> = mutableListOf()
    private val cachedAssets: MutableList<AssetModel> = mutableListOf()

    override suspend fun authenticateUser(tokenDTO: TokenDTO): Outcome<Unit, LunchError> {
        return withContext(dispatchers.io()) {
            runCatching {
                val userResponse = lunchService.getUser(tokenDTO.format())
                this@LunchRepository.token = tokenDTO.value
                this@LunchRepository.user = json
                    .encodeToString(UserResponse.serializer(), userResponse)
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun logoutUser(): Outcome<Unit, LunchError> {
        return runCatching {
            cachedCategories.clear()
            cachedAssets.clear()
            token = ""
            user = ""
        }.mapThrowable {
            LunchError.UnsuccessfulLogout
        }
    }

    override suspend fun getTransactions(): Outcome<List<TransactionModel>, LunchError> {
        return withContext(dispatchers.io()) {
            runCatching {
                mapTransactions(
                    lunchService.getTransactions(),
                    cachedCategories,
                    cachedAssets,
                )
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override fun getAssets(): List<AssetModel> = cachedAssets

    override suspend fun cacheTransactionCategories() {
        withContext(dispatchers.io()) {
            val categories = lunchService.getCategories().categories.map { it.toModel() }
            cachedCategories.clear()
            cachedCategories.addAll(categories)
        }
    }

    override suspend fun cacheAssets() {
        withContext(dispatchers.io()) {
            val assets = lunchService.getAssets().assets.map { it.toModel() }
            val plaidAccounts = lunchService.getPlaidAccounts().accounts.map { it.toModel() }
            cachedAssets.clear()
            cachedAssets.addAll(assets + plaidAccounts)
        }
    }

    override fun getSessionUser(): UserModel? {
        return if (user.isNotEmpty()) {
            json.decodeFromString(UserResponse.serializer(), user).toModel()
        } else {
            null
        }
    }

    override fun getSessionToken(): TokenDTO? {
        if (token.isNotEmpty()) {
            return TokenDTO(token)
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
