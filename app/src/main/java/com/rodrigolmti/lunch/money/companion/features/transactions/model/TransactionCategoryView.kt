package com.rodrigolmti.lunch.money.companion.features.transactions.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class TransactionCategoryView(
    val name: String,
    val id: Int,
)