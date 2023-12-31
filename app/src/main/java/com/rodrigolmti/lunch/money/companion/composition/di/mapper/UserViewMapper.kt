package com.rodrigolmti.lunch.money.companion.composition.di.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.features.settings.model.SettingsView

internal fun UserModel.toView() = SettingsView(
    userName = userName,
    userEmail = email,
)