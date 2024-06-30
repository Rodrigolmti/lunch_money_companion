package com.rodrigolmti.lunch.money.companion.features.transactions.model

data class TransactionMetadataView(
    val categories: List<String>,
    val location: String?,
    val logoURL: String?,
    val merchantName: String?,
    val paymentProcessor: String?,
    val pending: Boolean,
)