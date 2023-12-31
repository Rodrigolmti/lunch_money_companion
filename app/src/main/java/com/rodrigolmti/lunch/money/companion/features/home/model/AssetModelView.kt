package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

@Immutable
internal data class AssetModelView(
    val name: String,
    val balance: Double,
    val currency: String,
    val type: AssetTypeView,
)

internal fun fakeAssetModelView() = AssetModelView(
    name = ValueGenerator.gen(),
    balance = ValueGenerator.gen(),
    currency = "USD",
    type = AssetTypeView.entries.toTypedArray().random(),
)