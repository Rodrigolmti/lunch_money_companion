package com.rodrigolmti.lunch.money.companion.composition.domain.usecase

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching

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