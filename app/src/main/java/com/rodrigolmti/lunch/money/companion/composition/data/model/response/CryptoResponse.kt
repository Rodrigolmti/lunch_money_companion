package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class CryptoBodyResponse(
    @SerialName("crypto")
    val crypto: List<CryptoResponse>
)

@Keep
@Serializable
enum class CryptoSourceResponse {
    @SerialName("synced")
    SYNCED,

    @SerialName("manual")
    MANUAL,

    @SerialName("unknown")
    UNKNOWN,
}

@Keep
@Serializable
enum class CryptoStatusResponse {
    @SerialName("active")
    ACTIVE,

    @SerialName("inactive")
    INACTIVE,

    @SerialName("unknown")
    UNKNOWN,
}

@Keep
@Serializable
internal data class CryptoResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("zabo_account_id")
    val zaboAccountId: Int? = null,
    @SerialName("source")
    val source: CryptoSourceResponse = CryptoSourceResponse.UNKNOWN,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("name")
    val name: String,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("balance")
    val balance: String,
    @SerialName("balance_as_of")
    val balanceAsOf: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("status")
    val status: CryptoStatusResponse = CryptoStatusResponse.UNKNOWN,
    @SerialName("institution_name")
    val institutionName: String? = null,
)

