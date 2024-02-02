package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionDTO
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionStatus
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionStatusView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.UpdateTransactionView

/**
 * * -1 Multiplication is due to values being returned on the wrong scale;
 * Income have - and expenses have +.
 */
internal fun TransactionModel.toView() = TransactionView(
    id = id,
    date = date,
    amount = amount * -1,
    currency = currency,
    notes = notes,
    originalName = originalName,
    payee = payee,
    assetName = asset?.name,
    categoryName = category?.name,
    status = status.toView(),
)

internal fun UpdateTransactionView.toDto() = UpdateTransactionDTO(
    id = id,
    notes = notes,
)

internal fun TransactionStatus.toView() = when (this) {
    TransactionStatus.CLEARED -> TransactionStatusView.CLEARED
    TransactionStatus.PENDING -> TransactionStatusView.PENDING
    TransactionStatus.UNKNOWN -> TransactionStatusView.UNKNOWN
    TransactionStatus.RECURRING -> TransactionStatusView.RECURRING
    TransactionStatus.RECURRING_SUGGESTED -> TransactionStatusView.RECURRING_SUGGESTED
    TransactionStatus.UNCLEARED -> TransactionStatusView.UNCLEARED
    TransactionStatus.DELETE_PENDING -> TransactionStatusView.DELETE_PENDING
}