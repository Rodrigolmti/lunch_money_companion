package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import com.rodrigolmti.lunch.money.companion.composition.bridge.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import com.rodrigolmti.lunch.money.companion.core.DEFAULT_CURRENCY
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView

internal class SettingsFeatureAdapter(private val lunchRepository: IAppRepository) {

    fun getUserData(): Outcome<SettingsView, LunchError> {
        lunchRepository.getSessionUser()?.let { user ->
            val currency = lunchRepository.getPrimaryCurrency() ?: DEFAULT_CURRENCY
            return Outcome.success(user.toView(currency))
        } ?: return Outcome.failure(LunchError.InvalidDataError)
    }
}