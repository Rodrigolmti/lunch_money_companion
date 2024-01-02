package com.rodrigolmti.lunch.money.companion.composition.di.adapter

import com.rodrigolmti.lunch.money.companion.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.map
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date.time)
    }
}