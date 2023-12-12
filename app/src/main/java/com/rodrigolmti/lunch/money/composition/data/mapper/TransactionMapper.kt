package com.rodrigolmti.lunch.money.composition.data.mapper

import com.rodrigolmti.lunch.money.composition.data.model.response.AssetResponse
import com.rodrigolmti.lunch.money.composition.data.model.response.PlaidAccountResponse
import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionBodyResponse
import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionCategoryResponse
import com.rodrigolmti.lunch.money.composition.data.model.response.TransactionResponse
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetStatus
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionStatus

fun mapTransactions(
    response: TransactionBodyResponse,
    categories: List<TransactionCategoryModel>,
    assets: List<AssetModel>
): List<TransactionModel> = response.transactions.map { responseItem ->
    val category = categories.firstOrNull { category -> category.id == responseItem.categoryId }
    val asset = assets.firstOrNull { asset ->
        asset.id == responseItem.assetId || asset.id == responseItem.plaidAccountId
    }
    responseItem.toModel(category, asset)
}

fun TransactionResponse.toModel(
    category: TransactionCategoryModel?,
    asset: AssetModel?
) = TransactionModel(
    id = id,
    date = date,
    amount = amount.toFloatOrNull() ?: 0f,
    type = type,
    asset = asset,
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

fun PlaidAccountResponse.toModel() = AssetModel(
    id = id,
    typeName = type,
    subtypeName = subtype,
    name = name,
    balance = balance,
    balanceAsOf = balanceLastUpdate,
    currency = currency,
    institutionName = institutionName,
    status = mapStatus(),
)

fun AssetResponse.toModel() = AssetModel(
    id = id,
    typeName = typeName,
    subtypeName = subtypeName,
    name = name,
    balance = balance,
    balanceAsOf = balanceAsOf,
    currency = currency,
    institutionName = institutionName,
    status = AssetStatus.UNKNOWN,
)

fun TransactionCategoryResponse.toModel() = TransactionCategoryModel(
    id = id,
    description = description,
    excludeFromBudget = excludeFromBudget,
    excludeFromTotals = excludeFromTotals,
    isIncome = isIncome,
    name = name
)

private fun PlaidAccountResponse.mapStatus(): AssetStatus = when (status) {
    "active" -> AssetStatus.ACTIVE
    "inactive" -> AssetStatus.INACTIVE
    "relink" -> AssetStatus.RELINK
    "syncing" -> AssetStatus.SYNCING
    "error" -> AssetStatus.ERROR
    "not found" -> AssetStatus.NOT_FOUND
    "not supported" -> AssetStatus.NOT_SUPPORTED
    else -> AssetStatus.UNKNOWN
}

private fun TransactionResponse.mapStatus(): TransactionStatus = when (status) {
    "cleared" -> TransactionStatus.CLEARED
    "recurring" -> TransactionStatus.RECURRING
    "recurring_suggested" -> TransactionStatus.RECURRING_SUGGESTED
    "pending" -> TransactionStatus.PENDING
    else -> TransactionStatus.UNKNOWN
}