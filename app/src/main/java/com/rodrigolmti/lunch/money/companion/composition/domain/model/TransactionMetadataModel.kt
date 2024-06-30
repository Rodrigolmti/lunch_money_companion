package com.rodrigolmti.lunch.money.companion.composition.domain.model

data class TransactionMetadataModel(
    val categories: List<String>,
    val location: String?,
    val logoURL: String?,
    val merchantName: String?,
    val paymentProcessor: String?,
    val pending: Boolean,
)