package com.rodrigolmti.lunch.money.ui.features.transactions.data.model

enum class TransactionStatus {
    CLEARED, RECURRING, RECURRING_SUGGESTED, PENDING, UNKNOWN
}

data class TransactionCategoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    val isIncome: Boolean,
    val excludeFromBudget: Boolean,
    val excludeFromTotals: Boolean,
)

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
    val assetId: Int?,
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