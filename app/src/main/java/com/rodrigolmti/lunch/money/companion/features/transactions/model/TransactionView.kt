package com.rodrigolmti.lunch.money.companion.features.transactions.model

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

@Immutable
internal data class TransactionView(
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

internal fun fakeTransactionView() = TransactionView(
    id = ValueGenerator.genId(),
    date = ValueGenerator.genDate(),
    payee = ValueGenerator.gen(),
    amount = ValueGenerator.gen(),
    currency = ValueGenerator.gen(),
    notes = ValueGenerator.gen(),
    assetName = ValueGenerator.gen(),
    originalName = ValueGenerator.gen(),
    categoryName = ValueGenerator.gen(),
    status = TransactionStatusView.entries.toTypedArray().random(),
)