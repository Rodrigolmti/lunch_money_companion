package com.rodrigolmti.lunch.money.composition.data.repository

import com.rodrigolmti.lunch.money.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.composition.data.mapper.toModel
import com.rodrigolmti.lunch.money.composition.data.mapper.toResponse
import com.rodrigolmti.lunch.money.composition.data.model.model.Token
import com.rodrigolmti.lunch.money.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.core.IDispatchersProvider
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.SharedPreferencesDelegateFactory
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching
import com.rodrigolmti.lunch.money.ui.features.start.data.model.UserModel
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException

internal interface ILunchRepository {
    suspend fun authenticateUser(token: Token): Outcome<UserModel, LunchError>
    suspend fun storeUser(userModel: UserModel)
    fun getUser(): UserModel?
}

internal class LunchRepository(
    private val json: Json,
    private val lunchService: LunchService,
    private val dispatchers: IDispatchersProvider,
    sharedPreferences: SharedPreferencesDelegateFactory,
) : ILunchRepository {

    private var user: String by sharedPreferences.create("", "user_key")
    private var token: String by sharedPreferences.create("", "token_key")

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

    override suspend fun storeUser(userModel: UserModel) {
        val response = userModel.toResponse()
        user = json.encodeToString(UserResponse.serializer(), response)
    }

    override fun getUser(): UserModel? {
        return if (user.isNotEmpty()) {
            json.decodeFromString(UserResponse.serializer(), user).toModel()
        } else {
            null
        }
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
