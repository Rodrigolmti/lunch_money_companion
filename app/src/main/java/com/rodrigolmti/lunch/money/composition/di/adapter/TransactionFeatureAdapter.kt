package com.rodrigolmti.lunch.money.composition.di.adapter

import com.rodrigolmti.lunch.money.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.map
import com.rodrigolmti.lunch.money.features.transactions.model.TransactionView

internal class TransactionFeatureAdapter(private val lunchRepository: ILunchRepository) {

    suspend fun getTransactions(): Outcome<List<TransactionView>, LunchError> =
        lunchRepository.getTransactions().map { transactions ->
            transactions.map { transaction ->
                transaction.toView()
            }
        }
}