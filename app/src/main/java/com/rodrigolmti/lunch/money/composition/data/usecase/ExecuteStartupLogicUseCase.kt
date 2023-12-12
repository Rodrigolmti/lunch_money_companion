package com.rodrigolmti.lunch.money.composition.data.usecase

import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository

internal interface ExecuteStartupLogicUseCase {
    suspend operator fun invoke()
}

internal class ExecuteStartupLogic(
    private val lunchRepository: ILunchRepository
) : ExecuteStartupLogicUseCase {

    override suspend fun invoke() {
        try {
            lunchRepository.cacheTransactionCategories()
            lunchRepository.cacheAssets()
        } catch (e: Exception) {
            // TODO: // Send some logs here.
            print(e)
        }
    }
}