package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.map
import com.rodrigolmti.lunch.money.companion.features.recurring.RecurringView

internal class RecurringFeatureAdapter(private val lunchRepository: IAppRepository) {

    suspend fun getRecurring(): Outcome<List<RecurringView>, LunchError> {
        return lunchRepository.getRecurring().map { response ->
            response.map { it.toView() }
        }
    }
}