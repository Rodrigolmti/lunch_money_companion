package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class AssetTypeResponse {
    @SerialName("credit")
    CREDIT,

    @SerialName("depository")
    DEPOSITORY,

    @SerialName("brokerage")
    BROKERAGE,

    @SerialName("loan")
    LOAN,

    @SerialName("vehicle")
    VEHICLE,

    @SerialName("investment")
    INVESTMENT,

    @SerialName("other")
    OTHER_ASSETS,

    @SerialName("mortgage")
    OTHER_LIABILITIES,

    @SerialName("real_estate")
    REAL_STATE,

    @SerialName("cash")
    CASH,

    @SerialName("cryptocurrency")
    CRYPTOCURRENCY,

    @SerialName("employee_compensation")
    EMPLOYEE_COMPENSATION,

    @SerialName("unknown")
    UNKNOWN
}