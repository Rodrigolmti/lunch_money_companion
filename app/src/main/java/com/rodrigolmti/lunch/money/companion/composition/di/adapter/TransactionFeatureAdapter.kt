package com.rodrigolmti.lunch.money.companion.composition.di.adapter

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.companion.core.map

internal class TransactionFeatureAdapter(private val lunchRepository: ILunchRepository) {

    suspend fun getTransactions(): Outcome<List<TransactionView>, LunchError> =
        lunchRepository.getTransactions().map { transactions ->
            transactions.map { transaction ->
                transaction.toView()
            }
        }
}