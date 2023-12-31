package com.rodrigolmti.lunch.money.companion.features.settings.model

import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

data class SettingsView(
    val userName: String,
    val userEmail: String,
)

internal fun fakeSettingsView() = SettingsView(
    userName = ValueGenerator.gen(),
    userEmail = ValueGenerator.gen(),
)