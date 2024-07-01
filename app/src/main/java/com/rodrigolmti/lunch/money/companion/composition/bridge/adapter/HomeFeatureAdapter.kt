package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetStatus
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.DEFAULT_CURRENCY
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.getOrElse
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import com.rodrigolmti.lunch.money.companion.core.utils.mapEnumValue
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetTypeView
import com.rodrigolmti.lunch.money.companion.features.home.model.HomeView
import com.rodrigolmti.lunch.money.companion.features.home.model.PeriodSummaryView
import com.rodrigolmti.lunch.money.companion.features.home.model.SpendingBreakdownView
import com.rodrigolmti.lunch.money.companion.features.home.model.SpendingItemView
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
            val currency = lunchRepository.getPrimaryCurrency() ?: DEFAULT_CURRENCY

            val assets = lunchRepository.getAssets()
            val overviews = assets.groupBy { it.type }.map { (key, value) ->
                AssetOverviewView(
                    value.sumOf { it.balance },
                    mapEnumValue(key, AssetTypeView.UNKNOWN),
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
                    totalExpense = (totalExpense * -1),
                    netIncome = netIncome,
                    savingsRate = savingsRate.toInt(),
                    currency = transactions.firstOrNull()?.currency ?: currency
                ),
                pendingAssets = assets.filter {
                    it.status == AssetStatus.RELINK || it.status == AssetStatus.ERROR
                }.map { it.name },
                spendingBreakdown = mapSummaryBreakdown(
                    transactions,
                    currency,
                    totalIncome,
                    totalExpense,
                )
            )
        }.mapThrowable {
            LunchError.EmptyDataError
        }
    }

    private fun mapSummaryBreakdown(
        transactions: List<TransactionModel>,
        currency: String,
        totalIncome: Float,
        totalExpense: Float,
    ): SpendingBreakdownView {
        val incomes = mapSummaryBreakdownItem(
            transactions,
            { it.isIncome && !it.excludeFromTotals },
            { key, amount ->
                val normalizedAmount = AmountNormalizer.fixAmountBasedOnSymbol(
                    true,
                    amount.toFloat()
                )

                SpendingItemView(
                    name = key,
                    percentage = ((normalizedAmount / totalIncome) * 100).coerceAtLeast(0f)
                        .toInt(),
                    value = normalizedAmount,
                    currency = currency,
                )
            }
        )

        val expenses = mapSummaryBreakdownItem(
            transactions,
            { !it.isIncome && !it.excludeFromTotals },
            { key, amount ->
                val normalizedAmount = AmountNormalizer.fixAmountBasedOnSymbol(
                    false,
                    amount.toFloat()
                )

                SpendingItemView(
                    name = key,
                    percentage = ((amount / totalExpense) * 100).coerceAtLeast(0.0)
                        .toInt(),
                    value = normalizedAmount,
                    currency = currency,
                )
            }
        )

        return SpendingBreakdownView(
            incomes = incomes,
            expenses = expenses,
        )
    }

    private fun mapSummaryBreakdownItem(
        transactions: List<TransactionModel>,
        predicate: (TransactionModel) -> Boolean,
        mapper: (key: String, amount: Double) -> SpendingItemView,
    ): List<SpendingItemView> {
        val items = mutableListOf<SpendingItemView>()

        transactions
            .filter(predicate)
            .groupBy { it.category?.name ?: "uncategorized" }
            .mapValues { entry -> entry.value.sumOf { it.amount.toDouble() } }
            .asSequence()
            .forEach { (key, amount) ->
                items.add(mapper(key, amount))
            }

        return items.sortedByDescending { it.percentage }
    }

    private fun calculateTotalIncomeAndExpense(transactions: List<TransactionModel>): Pair<Float, Float> {
        val totalIncome = transactions.filter { it.isIncome && !it.excludeFromTotals }
            .sumOf { AmountNormalizer.normalizeAmount(it.amount).toDouble() }.toFloat()
        val totalExpense = transactions.filter { !it.isIncome && !it.excludeFromTotals }
            .sumOf { AmountNormalizer.normalizeAmount(it.amount).toDouble() }.toFloat()

        return totalIncome to totalExpense
    }
}

