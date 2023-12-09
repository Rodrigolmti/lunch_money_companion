package com.rodrigolmti.lunch.money.composition.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
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
    val apiKeyLabel: String,
)