package com.rodrigolmti.lunch.money.composition.data.mapper

import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionCategoryResponse
import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionResponse
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionStatus

fun TransactionResponse.toModel(category: TransactionCategoryModel?) = TransactionModel(
    id = id,
    date = date,
    amount = amount.toFloatOrNull() ?: 0f,
    type = type,
    assetId = assetId,
    category = category,
    currency = currency,
    externalId = externalId,
    fees = fees,
    groupId = groupId,
    isGroup = isGroup,
    notes = notes,
    originalName = originalName,
    parentId = parentId,
    payee = payee,
    plaidAccountId = plaidAccountId,
    price = price,
    quantity = quantity,
    recurringId = recurringId,
    status = mapStatus(),
    subtype = subtype,
    toBase = toBase
)

fun TransactionCategoryResponse.toModel() = TransactionCategoryModel(
    id = id,
    description = description,
    excludeFromBudget = excludeFromBudget,
    excludeFromTotals = excludeFromTotals,
    isIncome = isIncome,
    name = name
)

private fun TransactionResponse.mapStatus(): TransactionStatus = when (status) {
    "cleared" -> TransactionStatus.CLEARED
    "recurring" -> TransactionStatus.RECURRING
    "recurring_suggested" -> TransactionStatus.RECURRING_SUGGESTED
    "pending" -> TransactionStatus.PENDING
    else -> TransactionStatus.UNKNOWN
}