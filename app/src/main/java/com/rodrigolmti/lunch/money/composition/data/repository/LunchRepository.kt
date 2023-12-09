package com.rodrigolmti.lunch.money.composition.data.repository

import com.rodrigolmti.lunch.money.composition.data.LunchService
import com.rodrigolmti.lunch.money.composition.data.mapper.toModel
import com.rodrigolmti.lunch.money.core.IDispatchersProvider
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching
import com.rodrigolmti.lunch.money.ui.features.start.data.model.UserModel
import com.rodrigolmti.lunch.money.composition.di.LunchError
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface ILunchRepository {
    suspend fun getUser(token: Token): Outcome<UserModel, LunchError>
    suspend fun storeUser(user: UserModel)
}

class LunchRepository(
    private val lunchService: LunchService,
    private val dispatchers: IDispatchersProvider,
) : ILunchRepository {

    override suspend fun getUser(token: Token): Outcome<UserModel, LunchError> {
        return withContext(dispatchers.io()) {
            runCatching { lunchService.getUser(token.format()).toModel() }
                .mapThrowable {
                    handleNetworkError(it)
                }
        }
    }

    override suspend fun storeUser(user: UserModel) {

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
