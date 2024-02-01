package com.rodrigolmti.lunch.money.companion.composition.domain.model

import com.rodrigolmti.lunch.money.companion.core.utils.toDate
import java.util.Date

internal data class TransactionModel(
    val id: Int,
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
) {
    fun sortingDate(): Date {
        return date.toDate() ?: Date()
    }
}

