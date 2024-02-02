package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView

internal fun UserModel.toView(currency: String) = SettingsView(
    userName = userName,
    userEmail = email,
    currency = currency,
)