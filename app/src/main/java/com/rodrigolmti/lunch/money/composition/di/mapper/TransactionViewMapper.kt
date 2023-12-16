package com.rodrigolmti.lunch.money.composition.di.mapper

import com.rodrigolmti.lunch.money.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.composition.domain.model.TransactionStatus
import com.rodrigolmti.lunch.money.features.transactions.model.TransactionStatusView
import com.rodrigolmti.lunch.money.features.transactions.model.TransactionView

fun TransactionModel.toView() = TransactionView(
    id = id.toString(),
    date = date,
    amount = amount,
    currency = currency,
    notes = notes,
    originalName = originalName,
    payee = payee,
    assetName = asset?.name,
    categoryName = category?.name,
    status = status.toView(),
)

fun TransactionStatus.toView() = when (this) {
    TransactionStatus.CLEARED -> TransactionStatusView.CLEARED
    TransactionStatus.PENDING -> TransactionStatusView.PENDING
    TransactionStatus.UNKNOWN -> TransactionStatusView.UNKNOWN
    TransactionStatus.RECURRING -> TransactionStatusView.RECURRING
    TransactionStatus.RECURRING_SUGGESTED -> TransactionStatusView.RECURRING_SUGGESTED
    TransactionStatus.UNCLEARED -> TransactionStatusView.UNCLEARED
}