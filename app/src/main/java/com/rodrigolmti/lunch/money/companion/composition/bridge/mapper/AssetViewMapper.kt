package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetType
import com.rodrigolmti.lunch.money.companion.core.utils.mapEnumValue
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetModelView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetTypeView

internal fun AssetModel.toView() = AssetModelView(
    name = name,
    balance = balance,
    currency = currency,
    type = mapEnumValue(type, AssetTypeView.UNKNOWN)
)
