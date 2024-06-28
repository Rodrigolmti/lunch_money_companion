package com.rodrigolmti.lunch.money.companion.features.transactions.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class UpdateTransactionView(
    val id: Long,
    val notes: String? = null,
    val payee: String,
    val date: String,
    val category: TransactionCategoryView? = null
)