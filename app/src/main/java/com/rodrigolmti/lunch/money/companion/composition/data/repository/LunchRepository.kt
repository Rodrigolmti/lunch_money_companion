package com.rodrigolmti.lunch.money.companion.composition.data.repository

import com.rodrigolmti.lunch.money.companion.composition.data.mapper.mapBudget
import com.rodrigolmti.lunch.money.companion.composition.data.mapper.mapRecurrings
import com.rodrigolmti.lunch.money.companion.composition.data.mapper.mapTransaction
import com.rodrigolmti.lunch.money.companion.composition.data.mapper.mapTransactions
import com.rodrigolmti.lunch.money.companion.composition.data.mapper.mapUpdateTransaction
import com.rodrigolmti.lunch.money.companion.composition.data.mapper.toModel
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UpdateTransactionBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.companion.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.RecurringModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.ConnectionChecker
import com.rodrigolmti.lunch.money.companion.core.DEFAULT_EMPTY_STRING
import com.rodrigolmti.lunch.money.companion.core.IDispatchersProvider
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.SharedPreferencesDelegateFactory
import com.rodrigolmti.lunch.money.companion.core.cache.ICacheManager
import com.rodrigolmti.lunch.money.companion.core.logging.ICrashlyticsSdk
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.core.runCatching
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException

private const val USER_KEY = "user_key"
private const val TOKEN_KEY = "token_key"
private const val CURRENCY_KEY = "currency_key"

private const val CATEGORIES_CACHE = "categories_cache"
private const val ASSET_CACHE = "asset_cache"
private const val TRANSACTION_CACHE = "transaction_cache"

internal class LunchRepository(
    private val json: Json,
    private val lunchService: LunchService,
    cacheManager: ICacheManager,
    private val connectionChecker: ConnectionChecker,
    private val dispatchers: IDispatchersProvider,
    private val preferences: SharedPreferencesDelegateFactory,
    private val iCrashlyticsSdk: ICrashlyticsSdk,
) : ILunchRepository {

    private var user: String by preferences.create(DEFAULT_EMPTY_STRING, USER_KEY)
    private var token: String by preferences.create(DEFAULT_EMPTY_STRING, TOKEN_KEY)
    private var currency: String by preferences.create(DEFAULT_EMPTY_STRING, CURRENCY_KEY)

    private val _transactionUpdateFlow = MutableSharedFlow<Unit>(replay = 1)
    override val transactionUpdateFlow: SharedFlow<Unit> = _transactionUpdateFlow

    private val categoriesCache =
        cacheManager.createCache<String, List<TransactionCategoryModel>>(CATEGORIES_CACHE)
    private val assetCache = cacheManager.createCache<String, List<AssetModel>>(ASSET_CACHE)
    private val transactionCache =
        cacheManager.createCache<String, TransactionModel>(TRANSACTION_CACHE)

    override suspend fun authenticateUser(tokenDTO: TokenDTO): Outcome<Unit, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

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
            preferences.clear()
            categoriesCache.clear()
            transactionCache.clear()
            assetCache.clear()
        }.mapThrowable {
            iCrashlyticsSdk.logNonFatal(it)
            LunchError.UnsuccessfulLogoutError
        }
    }

    override suspend fun getTransactions(
        start: String,
        end: String,
    ): Outcome<List<TransactionModel>, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

        return withContext(dispatchers.io()) {
            runCatching {
                mapTransactions(
                    lunchService.getTransactions(start, end),
                    categoriesCache.get(CATEGORIES_CACHE, emptyList()),
                    assetCache.get(ASSET_CACHE, emptyList()),
                ).reversed()
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun getRecurring(): Outcome<List<RecurringModel>, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

        return withContext(dispatchers.io()) {
            runCatching {
                mapRecurrings(lunchService.getRecurring())
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun getTransaction(id: Long): Outcome<TransactionModel, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

        return withContext(dispatchers.io()) {
            runCatching {
                val transaction = mapTransaction(
                    lunchService.getTransaction(id),
                    categoriesCache.get(CATEGORIES_CACHE, emptyList()),
                    assetCache.get(ASSET_CACHE, emptyList()),
                )
                transactionCache.put(TRANSACTION_CACHE, transaction)
                transaction
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    override suspend fun updateTransaction(dto: UpdateTransactionDTO): Outcome<Unit, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

        return withContext(dispatchers.io()) {
            transactionCache.get(TRANSACTION_CACHE)?.let {
                runCatching {
                    lunchService.updateTransaction(
                        dto.id,
                        UpdateTransactionBodyResponse(
                            mapUpdateTransaction(dto)
                        )
                    )
                }.onSuccess {
                    _transactionUpdateFlow.emit(Unit)
                }.mapThrowable { throwable ->
                    handleNetworkError(throwable)
                }
            } ?: throw IllegalStateException("Transaction not found in cache")
        }
    }

    override fun getAssets(): List<AssetModel> = assetCache.get(ASSET_CACHE, emptyList())

    override suspend fun cacheTransactionCategories() {
        withContext(dispatchers.io()) {
            val categories = lunchService.getCategories().categories.map { it.toModel() }
            categoriesCache.clear()
            categoriesCache.put(CATEGORIES_CACHE, categories)
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

    override suspend fun getBudgets(
        start: String,
        end: String,
    ): Outcome<List<BudgetModel>, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

        return withContext(dispatchers.io()) {
            runCatching {
                val response = lunchService.getBudgets(start, end)
                mapBudget(response)
            }.mapThrowable {
                handleNetworkError(it)
            }
        }
    }

    private fun handleNetworkError(throwable: Throwable): LunchError {
        val statusCode = when (throwable) {
            is HttpException -> {
                throwable.code()
            }

            else -> {
                -1
            }
        }

        val error = LunchError.NetworkError(
            throwable = throwable,
            message = throwable.message ?: DEFAULT_EMPTY_STRING,
            code = statusCode,
        )

        iCrashlyticsSdk.logNonFatal(error.throwable)

        return error
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

    override fun updatePrimaryCurrency(currency: String) {
        this.currency = currency
    }

    override fun getPrimaryCurrency(): String? {
        return currency.ifEmpty { null }
    }

    override fun getCategories(): List<TransactionCategoryModel> {
        return categoriesCache.get(CATEGORIES_CACHE, emptyList())
    }
}
