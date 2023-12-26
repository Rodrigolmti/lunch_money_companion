package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AssetsBodyResponse(
    @SerialName("assets")
    val assets: List<AssetResponse>,
)

@Serializable
internal data class AssetResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("type_name")
    val type: AssetTypeResponse = AssetTypeResponse.UNKNOWN,
    @SerialName("subtype_name")
    val subtypeName: String,
    @SerialName("name")
    val name: String,
    @SerialName("balance")
    val balance: String,
    @SerialName("balance_as_of")
    val balanceAsOf: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("institution_name")
    val institutionName: String? = null,
)

