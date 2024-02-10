package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toDto
import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.DEFAULT_CURRENCY
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.map
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionDetailView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.UpdateTransactionView
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary.TransactionSummaryView
import java.util.Date

internal class TransactionFeatureAdapter(
    private val lunchRepository: ILunchRepository
) {

    suspend fun getTransactionSummary(
        start: Date,
        end: Date
    ): Outcome<TransactionSummaryView, LunchError> {
        val currency = lunchRepository.getPrimaryCurrency() ?: DEFAULT_CURRENCY

        return lunchRepository.getTransactions(formatDate(start), formatDate(end))
            .map { transactions ->
                val income = mutableMapOf<String, Float>()
                transactions.filter { it.isIncome && !it.excludeFromTotals }.forEach {
                    val key = it.category?.name ?: "uncategorized"
                    val value = income[key] ?: 0.0f
                    income[key] = (value + it.amount)
                }

                val expense = mutableMapOf<String, Float>()
                transactions.filter { !it.isIncome && !it.excludeFromTotals }.forEach {
                    val key = it.category?.name ?: "uncategorized"
                    val value = expense[key] ?: 0.0f
                    expense[key] = (value + it.amount)
                }

                val totalIncome = income.values.sum() * -1
                val totalExpense = expense.values.sum()

                TransactionSummaryView(
                    income = income,
                    totalIncome = totalIncome,
                    expense = expense,
                    totalExpense = totalExpense,
                    net = totalIncome - totalExpense,
                    currency = transactions.firstOrNull()?.currency ?: currency
                )
            }
    }

    suspend fun getTransactions(
        start: Date,
        end: Date
    ): Outcome<List<TransactionView>, LunchError> =
        lunchRepository.getTransactions(formatDate(start), formatDate(end)).map { transactions ->
            transactions.map { transaction ->
                transaction.toView()
            }
        }

    suspend fun getTransaction(
        id: Int
    ): Outcome<TransactionDetailView, LunchError> {
        val categories = lunchRepository.getCategories()

        return lunchRepository.getTransaction(id).map { transaction ->
            transaction.toView()
        }.map {
            TransactionDetailView(
                transaction = it,
                categories = categories.map { category ->
                    category.toView()
                }
            )
        }
    }

    suspend fun updateTransaction(
        model: UpdateTransactionView
    ): Outcome<Unit, LunchError> {
        return lunchRepository.updateTransaction(model.toDto())
    }
}