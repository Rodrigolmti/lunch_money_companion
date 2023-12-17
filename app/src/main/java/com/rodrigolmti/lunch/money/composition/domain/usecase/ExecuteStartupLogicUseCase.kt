package com.rodrigolmti.lunch.money.composition.domain.usecase

import com.rodrigolmti.lunch.money.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching

internal interface ExecuteStartupLogicUseCase {
    suspend operator fun invoke(): Outcome<Unit, LunchError>
}

internal class ExecuteStartupLogic(
    private val lunchRepository: ILunchRepository
) : ExecuteStartupLogicUseCase {

    override suspend fun invoke(): Outcome<Unit, LunchError> {
        return runCatching {
            lunchRepository.cacheTransactionCategories()
            lunchRepository.cacheAssets()
        }.mapThrowable {
            LunchError.UnknownError
        }
    }
}