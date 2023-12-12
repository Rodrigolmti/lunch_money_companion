package com.rodrigolmti.lunch.money.composition.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaidAccountBodyResponse(
    @SerialName("plaid_accounts")
    val accounts: List<PlaidAccountResponse>,
)

@Serializable
data class PlaidAccountResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String,
    @SerialName("subtype")
    val subtype: String,
    @SerialName("institution_name")
    val institutionName: String? = null,
    @SerialName("status")
    val status: String,
    @SerialName("balance")
    val balance: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("balance_last_update")
    val balanceLastUpdate: String,
)