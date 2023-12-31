package com.rodrigolmti.lunch.money.companion.composition.di.adapter

import com.rodrigolmti.lunch.money.companion.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView

internal class SettingsFeatureAdapter(private val lunchRepository: ILunchRepository) {

    fun getUserData(): Outcome<SettingsView, LunchError> {
        lunchRepository.getSessionUser()?.let { user ->
            return Outcome.success(user.toView())
        } ?: return Outcome.failure(LunchError.InvalidDataError)
    }
}