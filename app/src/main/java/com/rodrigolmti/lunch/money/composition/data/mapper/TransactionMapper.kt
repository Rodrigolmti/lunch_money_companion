package com.rodrigolmti.lunch.money.composition.data.mapper

import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionResponse
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel

fun TransactionResponse.toModel() = TransactionModel(
    id = id,
    date = date,
    amount = amount,
    type = type,
    assetId = assetId,
    categoryId = categoryId,
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
    status = status,
    subtype = subtype,
    toBase = toBase
)