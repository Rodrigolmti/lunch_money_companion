package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal enum class AssetType {
    CASH,
    CREDIT,
    INVESTMENT,
    REAL_STATE,
    DEPOSITORY,
    BROKERAGE,
    LOAN,
    VEHICLE,
    CRYPTOCURRENCY,
    EMPLOYEE_COMPENSATION,
    OTHER_LIABILITIES,
    OTHER_ASSETS,
    UNKNOWN,
}

internal enum class AssetSource {
    SYNCED,
    MANUAL,
    UNKNOWN,
}

internal data class AssetModel(
    val id: Int,
    val type: AssetType,
    val subtypeName: String?,
    val name: String,
    val balance: Double,
    val currency: String,
    val institutionName: String?,
    val status: AssetStatus,
    val source : AssetSource = AssetSource.UNKNOWN,
)

