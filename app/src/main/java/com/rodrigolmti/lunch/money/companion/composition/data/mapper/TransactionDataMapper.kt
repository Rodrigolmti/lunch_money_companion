package com.rodrigolmti.lunch.money.companion.composition.data.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.AssetResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.AssetTypeResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CryptoResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CryptoSourceResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.CryptoStatusResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.PlaidAccountResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.PlaidAccountStatus
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionCategoryResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionMetadataResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.TransactionStatusResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UpdateTransactionResponse
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetSource
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetStatus
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetType
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionMetadataModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionStatus

internal fun mapTransactions(
    response: TransactionBodyResponse,
    categories: List<TransactionCategoryModel>,
    assets: List<AssetModel>
): List<TransactionModel> = response.transactions.map { responseItem ->
    mapTransaction(responseItem, categories, assets)
}

internal fun mapUpdateTransaction(model: UpdateTransactionDTO): UpdateTransactionResponse =
    UpdateTransactionResponse(
        date = model.date,
        categoryId = model.category?.id,
        notes = model.notes,
        payee = model.payee,
    )

internal fun mapTransaction(
    responseItem: TransactionResponse,
    categories: List<TransactionCategoryModel>,
    assets: List<AssetModel>,
): TransactionModel {
    val category = categories.firstOrNull { category -> category.id == responseItem.categoryId }
    val asset = assets.firstOrNull { asset ->
        asset.id == responseItem.assetId || asset.id == responseItem.plaidAccountId
    }
    return responseItem.toModel(category, asset)
}

internal fun TransactionResponse.toModel(
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
    notes = notes,
    originalName = originalName,
    payee = payee,
    plaidAccountId = plaidAccountId,
    recurringId = recurringId,
    status = mapStatus(),
    subtype = subtype,
    toBase = toBase,
    excludeFromTotals = excludeFromTotals ?: false,
    isIncome = isIncome ?: false,
    metadata = metadata?.toModel(),
)

internal fun TransactionMetadataResponse.toModel() : TransactionMetadataModel {
    val stringBuilder: StringBuilder = StringBuilder()
    location?.city?.let {
        stringBuilder.append(it)
    }
    location?.region?.let {
        stringBuilder.append(" - $it")
    }
    location?.country?.let {
        stringBuilder.append(" - $it")
    }

    val location = stringBuilder.toString()

    return TransactionMetadataModel(
        categories = categories ?: emptyList(),
        location = location.ifEmpty { null },
        logoURL = logoURL,
        merchantName = merchantName,
        paymentProcessor = payment?.paymentProcessor,
        pending = pending ?: true,
    )
}

internal fun PlaidAccountResponse.toModel() = AssetModel(
    id = id,
    type = type.mapType(),
    subtypeName = subtype,
    name = displayName ?: name,
    balance = balance.toDoubleOrNull() ?: 0.0,
    balanceAsOf = balanceLastUpdate,
    currency = currency,
    institutionName = institutionName,
    status = status.mapStatus(),
)

internal fun CryptoResponse.toModel() = AssetModel(
    id = (id ?: zaboAccountId) ?: -1,
    type = AssetType.CRYPTOCURRENCY,
    subtypeName = "crypto",
    name = displayName ?: name,
    source = source.mapStatus(),
    balance = balance.toDoubleOrNull() ?: 0.0,
    balanceAsOf = balanceAsOf,
    currency = currency,
    institutionName = institutionName,
    status = status.mapStatus(),
)

internal fun AssetResponse.toModel() = AssetModel(
    id = id,
    type = type.mapType(),
    subtypeName = subtypeName,
    name = displayName ?: name,
    balance = balance.toDoubleOrNull() ?: 0.0,
    balanceAsOf = balanceAsOf,
    currency = currency,
    institutionName = institutionName,
    status = AssetStatus.UNKNOWN,
)

internal fun CryptoSourceResponse.mapStatus() = when (this) {
    CryptoSourceResponse.MANUAL -> AssetSource.MANUAL
    CryptoSourceResponse.SYNCED -> AssetSource.SYNCED
    CryptoSourceResponse.UNKNOWN -> AssetSource.UNKNOWN
}

internal fun TransactionCategoryResponse.toModel() = TransactionCategoryModel(
    id = id,
    description = description,
    excludeFromBudget = excludeFromBudget,
    excludeFromTotals = excludeFromTotals,
    isIncome = isIncome,
    name = name
)

private fun CryptoStatusResponse.mapStatus(): AssetStatus = when (this) {
    CryptoStatusResponse.ACTIVE -> AssetStatus.ACTIVE
    CryptoStatusResponse.INACTIVE -> AssetStatus.INACTIVE
    CryptoStatusResponse.UNKNOWN -> AssetStatus.UNKNOWN
}

private fun AssetTypeResponse.mapType(): AssetType = when (this) {
    AssetTypeResponse.CREDIT -> AssetType.CREDIT
    AssetTypeResponse.DEPOSITORY -> AssetType.DEPOSITORY
    AssetTypeResponse.BROKERAGE -> AssetType.BROKERAGE
    AssetTypeResponse.LOAN -> AssetType.LOAN
    AssetTypeResponse.VEHICLE -> AssetType.VEHICLE
    AssetTypeResponse.INVESTMENT -> AssetType.INVESTMENT
    AssetTypeResponse.OTHER_ASSETS -> AssetType.OTHER_ASSETS
    AssetTypeResponse.OTHER_LIABILITIES -> AssetType.OTHER_LIABILITIES
    AssetTypeResponse.REAL_STATE -> AssetType.REAL_STATE
    AssetTypeResponse.CASH -> AssetType.CASH
    AssetTypeResponse.CRYPTOCURRENCY -> AssetType.CRYPTOCURRENCY
    AssetTypeResponse.EMPLOYEE_COMPENSATION -> AssetType.EMPLOYEE_COMPENSATION
    AssetTypeResponse.UNKNOWN -> AssetType.UNKNOWN
}

private fun PlaidAccountStatus.mapStatus(): AssetStatus = when (this) {
    PlaidAccountStatus.ACTIVE -> AssetStatus.ACTIVE
    PlaidAccountStatus.INACTIVE -> AssetStatus.INACTIVE
    PlaidAccountStatus.ERROR -> AssetStatus.ERROR
    PlaidAccountStatus.NOT_FOUND -> AssetStatus.NOT_FOUND
    PlaidAccountStatus.NOT_SUPPORTED -> AssetStatus.NOT_SUPPORTED
    PlaidAccountStatus.RELINK -> AssetStatus.RELINK
    PlaidAccountStatus.SYNCING -> AssetStatus.SYNCING
    PlaidAccountStatus.UNKNOWN -> AssetStatus.UNKNOWN
}

private fun TransactionResponse.mapStatus(): TransactionStatus = when (status) {
    TransactionStatusResponse.CLEARED -> TransactionStatus.CLEARED
    TransactionStatusResponse.PENDING -> TransactionStatus.PENDING
    TransactionStatusResponse.RECURRING -> TransactionStatus.RECURRING
    TransactionStatusResponse.UNCLEARED -> TransactionStatus.UNCLEARED
    TransactionStatusResponse.RECURRING_SUGGESTED -> TransactionStatus.RECURRING_SUGGESTED
    TransactionStatusResponse.DELETE_PENDING -> TransactionStatus.DELETE_PENDING
    TransactionStatusResponse.UNKNOWN -> TransactionStatus.UNKNOWN
}