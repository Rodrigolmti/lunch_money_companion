package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class AssetModelView(
    val name: String,
    val balance: Double,
    val currency: String,
)