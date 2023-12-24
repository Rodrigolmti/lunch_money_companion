package com.rodrigolmti.lunch.money.companion.features.transactions.model

import androidx.compose.runtime.Immutable

@Immutable
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

