package com.rodrigolmti.lunch.money.companion.composition.domain.model

data class TransactionModel(
    val id: Int,
    val date: String,
    val payee: String,
    val amount: Float,
    val currency: String,
    val toBase: Double,
    val notes: String?,
    val category: TransactionCategoryModel?,
    val recurringId: Int?,
    val asset: AssetModel?,
    val plaidAccountId: Int?,
    val status: TransactionStatus,
    val isGroup: Boolean,
    val groupId: Int?,
    val parentId: Int?,
    val externalId: Int?,
    val originalName: String?,
    val type: String?,
    val subtype: String?,
    val fees: String?,
    val price: String?,
    val quantity: String?
)

