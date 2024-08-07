package com.rodrigolmti.lunch.money.companion.composition.domain.usecase

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import com.rodrigolmti.lunch.money.companion.core.ConnectionChecker
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.logging.ICrashlyticsSdk
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching

internal interface ExecuteStartupLogicUseCase {
    suspend operator fun invoke(): Outcome<Unit, LunchError>
}

internal class ExecuteStartupLogic(
    private val lunchRepository: IAppRepository,
    private val connectionChecker: ConnectionChecker,
    private val iCrashlyticsSdk: ICrashlyticsSdk,
) : ExecuteStartupLogicUseCase {

    override suspend fun invoke(): Outcome<Unit, LunchError> {
        if (!connectionChecker.isConnected()) return Outcome.failure(LunchError.NoConnectionError)

        return runCatching {

            iCrashlyticsSdk.setCollectionEnabled(true)

            lunchRepository.cacheTransactionCategories()
            lunchRepository.cacheAssets()
        }.mapThrowable {
            iCrashlyticsSdk.logNonFatal(it)
            LunchError.UnknownError
        }
    }
}