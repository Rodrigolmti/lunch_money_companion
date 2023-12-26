package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal data class UserModel(
    val userName: String,
    val email: String,
    val id: Int,
    val accountId: Int,
    val budgetName: String,
    val apiKeyLabel: String,
)