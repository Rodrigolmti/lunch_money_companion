package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class UserResponse(
    @SerialName("user_name")
    val userName: String,
    @SerialName("user_email")
    val email: String,
    @SerialName("user_id")
    val id: Int,
    @SerialName("account_id")
    val accountId: Int,
    @SerialName("budget_name")
    val budgetName: String,
    @SerialName("api_key_label")
    val apiKeyLabel: String? = null,
)