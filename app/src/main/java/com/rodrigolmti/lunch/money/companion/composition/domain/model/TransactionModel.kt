package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal data class TransactionModel(
    val id: Long,
    val date: String,
    val payee: String,
    val amount: Float,
    val isIncome: Boolean,
    val excludeFromTotals: Boolean,
    val currency: String,
    val toBase: Double,
    val notes: String?,
    val category: TransactionCategoryModel?,
    val recurringId: Int?,
    val asset: AssetModel?,
    val plaidAccountId: Int?,
    val status: TransactionStatus,
    val originalName: String?,
    val type: String?,
    val subtype: String?,
    val metadata: TransactionMetadataModel?
)
