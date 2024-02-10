package com.rodrigolmti.lunch.money.companion.features.transactions.model

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

@Immutable
internal data class TransactionDetailView(
    val transaction: TransactionView,
    val categories: List<TransactionCategoryView>,
)

@Immutable
internal data class TransactionView(
    val id: Int,
    val date: String,
    val payee: String,
    val amount: Float,
    val currency: String,
    val notes: String?,
    val assetName: String?,
    val originalName: String?,
    val category: TransactionCategoryView?,
    val status: TransactionStatusView,
)

internal fun fakeTransactionDetailView() = TransactionDetailView(
    transaction = fakeTransactionView(),
    categories = (0..5).map {
        TransactionCategoryView(
            ValueGenerator.gen(),
            ValueGenerator.gen()
        )
    }
)

internal fun fakeTransactionView(
    status: TransactionStatusView = TransactionStatusView.entries.toTypedArray().random()
) = TransactionView(
    id = ValueGenerator.gen(),
    date = ValueGenerator.date("dd/MM/yyyy"),
    payee = ValueGenerator.gen(),
    amount = ValueGenerator.gen(),
    currency = ValueGenerator.currency(),
    notes = ValueGenerator.gen(),
    assetName = ValueGenerator.gen(),
    originalName = ValueGenerator.gen(),
    category = TransactionCategoryView(
        ValueGenerator.gen(),
        ValueGenerator.gen()
    ),
    status = status,
)