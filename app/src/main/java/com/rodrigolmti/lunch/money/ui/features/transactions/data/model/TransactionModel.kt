package com.rodrigolmti.lunch.money.ui.features.transactions.data.model

class TransactionModel(
    val id: Int,
    val date: String,
    val payee: String,
    val amount: String,
    val currency: String,
    val toBase: Double,
    val notes: String?,
    val categoryId: Int?,
    val recurringId: Int?,
    val assetId: Int?,
    val plaidAccountId: Int?,
    val status: String,
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