package com.rodrigolmti.lunch.money.companion.composition.data.repository

import com.rodrigolmti.lunch.money.companion.composition.data.mapper.mapTransactions
import com.rodrigolmti.lunch.money.companion.composition.data.mapper.toModel
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.companion.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.DEFAULT_EMPTY_STRING
import com.rodrigolmti.lunch.money.companion.core.IDispatchersProvider
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.SharedPreferencesDelegateFactory
import com.rodrigolmti.lunch.money.companion.core.cache.ICacheManager
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException

private const val USER_KEY = "user_key"
private const val TOKEN_KEY = "token_key"

private const val TRANSACTION_CACHE = "transaction_cache"
private const val ASSET_CACHE = "asset_cache"

internal class LunchRepository(
    private val json: Json,
    private val lunchService: LunchService,
    cacheManager: ICacheManager,
    private val dispatchers: IDispatchersProvider,
    preferences: SharedPreferencesDelegateFactory,
) : ILunchRepository {

    private var user: String by preferences.create(DEFAULT_EMPTY_STRING, USER_KEY)
    private var token: String by preferences.create(DEFAULT_EMPTY_STRING, TOKEN_KEY)

    private val transactionCache =
        cacheManager.createCache<String, List<TransactionCategoryModel>>(TRANSACTION_CACHE)
    private val assetCache = cacheManager.createCache<String, List<AssetModel>>(ASSET_CACHE)

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
            token = DEFAULT_EMPTY_STRING
            user = DEFAULT_EMPTY_STRING
            transactionCache.clear()
            assetCache.clear()
        }.mapThrowable {
            LunchError.UnsuccessfulLogout
        }
    }

    override suspend fun getTransactions(
        start: String,
        end: String,
    ): Outcome<List<TransactionModel>, LunchError> {
        return withContext(dispatchers.io()) {
            runCatching {
                mapTransactions(
                    lunchService.getTransactions(start, end),
                    transactionCache.get(TRANSACTION_CACHE, emptyList()),
                    assetCache.get(ASSET_CACHE, emptyList()),
                ).reversed()
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override fun getAssets(): List<AssetModel> = assetCache.get(ASSET_CACHE, emptyList())

    override suspend fun cacheTransactionCategories() {
        withContext(dispatchers.io()) {
            val categories = lunchService.getCategories().categories.map { it.toModel() }
            transactionCache.clear()
            transactionCache.put(TRANSACTION_CACHE, categories)
        }
    }

    override suspend fun cacheAssets() {
        withContext(dispatchers.io()) {
            val assets = lunchService.getAssets().assets.map { it.toModel() }
            val plaidAccounts = lunchService.getPlaidAccounts().accounts.map { it.toModel() }
            val crypto = lunchService.getCrypto().crypto.map { it.toModel() }
            assetCache.clear()
            assetCache.put(ASSET_CACHE, assets + plaidAccounts + crypto)
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
                    message = throwable.message ?: DEFAULT_EMPTY_STRING,
                    code = throwable.code(),
                )
            }

            else -> {
                return LunchError.NetworkError(
                    throwable = throwable,
                    message = throwable.message ?: DEFAULT_EMPTY_STRING,
                    code = -1,
                )
            }
        }
    }
}
