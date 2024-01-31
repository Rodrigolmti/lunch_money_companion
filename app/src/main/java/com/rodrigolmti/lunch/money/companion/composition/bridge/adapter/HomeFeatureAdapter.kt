package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetStatus
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.getOrElse
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import com.rodrigolmti.lunch.money.companion.features.home.model.HomeView
import com.rodrigolmti.lunch.money.companion.features.home.model.PeriodSummaryView
import kotlinx.collections.immutable.toImmutableList
import java.util.Date

internal class HomeFeatureAdapter(
    private val lunchRepository: ILunchRepository,
) {

    suspend fun getAssetOverview(
        start: Date,
        end: Date
    ): Outcome<HomeView, LunchError> {
        return runCatching {
            val assets = lunchRepository.getAssets()
            val overviews = assets.groupBy { it.type }.map { (key, value) ->
                AssetOverviewView(
                    value.sumOf { it.balance },
                    key.toView(),
                    value.map { asset -> asset.toView() },
                )
            }

            val result = lunchRepository.getTransactions(formatDate(start), formatDate(end))
            val transactions = result.getOrElse { emptyList() }

            val (totalIncome, totalExpense) = calculateTotalIncomeAndExpense(transactions)
            val netIncome = totalIncome - totalExpense
            val savingsRate = if (totalIncome > 0) (netIncome / totalIncome) * 100 else 0.0

            HomeView(
                overviews = overviews.toImmutableList(),
                summary = PeriodSummaryView(
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    netIncome = netIncome,
                    savingsRate = (savingsRate.toFloat()).toInt(),
                    currency = transactions.first().currency
                ),
                pendingAssets = assets.filter {
                    it.status == AssetStatus.RELINK || it.status == AssetStatus.ERROR
                }.map { it.name },
            )
        }.mapThrowable {
            LunchError.EmptyDataError
        }
    }

    private fun calculateTotalIncomeAndExpense(transactions: List<TransactionModel>): Pair<Float, Float> {
        val totalIncome = transactions.filter { it.isIncome && !it.excludeFromTotals }
            .sumOf { it.amount.toDouble() }.toFloat() * -1
        val totalExpense = transactions.filter { !it.isIncome && !it.excludeFromTotals }
            .sumOf { it.amount.toDouble() }.toFloat()

        return totalIncome to totalExpense
    }
}