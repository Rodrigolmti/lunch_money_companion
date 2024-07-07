package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.GetTransactionSumByCategoryUseCase
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.getOrElse
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import java.util.Date

internal class AnalyzeFeatureAdapter(
    val sumTransactionUseCase: GetTransactionSumByCategoryUseCase,
    private val lunchRepository: IAppRepository,
) {

    suspend fun getSumGroupedTransactions(
        start: Date,
        end: Date
    ): Outcome<Map<String, Float>, LunchError> {
        return runCatching {
            val result = lunchRepository.getTransactions(formatDate(start), formatDate(end))
            val transactions = result.getOrElse { emptyList() }

            sumTransactionUseCase(transactions)
        }.mapThrowable {
            LunchError.EmptyDataError
        }
    }
}