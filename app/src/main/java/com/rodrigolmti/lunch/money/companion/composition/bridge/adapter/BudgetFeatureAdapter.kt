package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.DEFAULT_CURRENCY
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.map
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView
import java.util.Date

private const val CATEGORY_FILTER_KEY = "uncategorized"

internal class BudgetFeatureAdapter(private val lunchRepository: ILunchRepository) {

    suspend fun getBudget(
        start: Date,
        end: Date
    ): Outcome<List<BudgetView>, LunchError> {
        val currency = lunchRepository.getPrimaryCurrency() ?: DEFAULT_CURRENCY

        return lunchRepository.getBudgets(formatDate(start), formatDate(end)).map { response ->
            response.map { it.toView(currency) }.filter {
                it.category.compareTo(CATEGORY_FILTER_KEY, true) != 0
            }
        }
    }
}