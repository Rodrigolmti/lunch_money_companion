package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.map
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import java.util.Date

internal class TransactionFeatureAdapter(private val lunchRepository: ILunchRepository) {

    suspend fun getTransactions(
        start: Date,
        end: Date
    ): Outcome<List<TransactionView>, LunchError> =
        lunchRepository.getTransactions(formatDate(start), formatDate(end)).map { transactions ->
            transactions.map { transaction ->
                transaction.toView()
            }
        }
}