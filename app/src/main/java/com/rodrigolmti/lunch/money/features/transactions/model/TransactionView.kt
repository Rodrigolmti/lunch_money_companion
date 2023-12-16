package com.rodrigolmti.lunch.money.features.transactions.model

data class TransactionView(
    val id: String,
    val date: String,
    val payee: String,
    val amount: Float,
    val currency: String,
    val notes: String?,
    val assetName: String?,
    val originalName: String?,
    val categoryName: String?,
    val status: TransactionStatusView,
)

