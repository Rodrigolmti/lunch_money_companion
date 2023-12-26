package com.rodrigolmti.lunch.money.companion.composition.di.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetType
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetModelView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetTypeView

internal fun AssetType.toView(): AssetTypeView = when (this) {
    AssetType.CASH -> AssetTypeView.CASH
    AssetType.INVESTMENT -> AssetTypeView.INVESTMENT
    AssetType.LOAN -> AssetTypeView.LOAN
    AssetType.CREDIT -> AssetTypeView.CREDIT
    AssetType.REAL_STATE -> AssetTypeView.REAL_STATE
    AssetType.DEPOSITORY -> AssetTypeView.DEPOSITORY
    AssetType.BROKERAGE -> AssetTypeView.BROKERAGE
    AssetType.VEHICLE -> AssetTypeView.VEHICLE
    AssetType.CRYPTOCURRENCY -> AssetTypeView.CRYPTOCURRENCY
    AssetType.EMPLOYEE_COMPENSATION -> AssetTypeView.EMPLOYEE_COMPENSATION
    AssetType.OTHER_LIABILITIES -> AssetTypeView.OTHER_LIABILITIES
    AssetType.OTHER_ASSETS -> AssetTypeView.OTHER_ASSETS
    AssetType.UNKNOWN -> AssetTypeView.UNKNOWN
}

internal fun AssetModel.toView() = AssetModelView(
    name = name,
    balance = balance,
    currency = currency,
)