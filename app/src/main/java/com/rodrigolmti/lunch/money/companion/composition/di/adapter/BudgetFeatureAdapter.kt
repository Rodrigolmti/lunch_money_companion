package com.rodrigolmti.lunch.money.companion.composition.di.adapter

import com.rodrigolmti.lunch.money.companion.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.map
import com.rodrigolmti.lunch.money.companion.core.utils.formatDate
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView
import java.util.Date

internal class BudgetFeatureAdapter(private val lunchRepository: ILunchRepository) {

    suspend fun getBudget(
        start: Date,
        end: Date
    ): Outcome<List<BudgetView>, LunchError> {
        return lunchRepository.getBudgets(formatDate(start), formatDate(end)).map { response ->
            response.map { it.toView() }.filter { it.category != "Uncategorized" }
        }
    }
}