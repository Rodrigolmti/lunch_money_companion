package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class PlaidAccountBodyResponse(
    @SerialName("plaid_accounts")
    val accounts: List<PlaidAccountResponse>,
)

@Keep
@Serializable
internal data class PlaidAccountResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("type")
    val type: AssetTypeResponse = AssetTypeResponse.UNKNOWN,
    @SerialName("subtype")
    val subtype: String? = null,
    @SerialName("institution_name")
    val institutionName: String? = null,
    @SerialName("status")
    val status: PlaidAccountStatus = PlaidAccountStatus.UNKNOWN,
    @SerialName("balance")
    val balance: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("balance_last_update")
    val balanceLastUpdate: String,
)

@Keep
@Serializable
internal enum class PlaidAccountStatus {
    @SerialName("active")
    ACTIVE,

    @SerialName("inactive")
    INACTIVE,

    @SerialName("relink")
    RELINK,

    @SerialName("syncing")
    SYNCING,

    @SerialName("error")
    ERROR,

    @SerialName("not found")
    NOT_FOUND,

    @SerialName("not supported")
    NOT_SUPPORTED,

    @SerialName("unknown")
    UNKNOWN,
}