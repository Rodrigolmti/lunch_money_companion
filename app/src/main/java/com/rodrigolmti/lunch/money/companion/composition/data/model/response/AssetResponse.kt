package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class AssetsBodyResponse(
    @SerialName("assets")
    val assets: List<AssetResponse>,
)

@Keep
@Serializable
internal data class AssetResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("type_name")
    val type: AssetTypeResponse = AssetTypeResponse.UNKNOWN,
    @SerialName("subtype_name")
    val subtypeName: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("balance")
    val balance: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("institution_name")
    val institutionName: String? = null,
)

