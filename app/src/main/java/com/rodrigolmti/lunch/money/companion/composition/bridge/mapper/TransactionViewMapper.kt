package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionCategoryDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionDTO
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionMetadataModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.core.utils.mapEnumValue
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionCategoryView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionMetadataView
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
    category = category?.toView(),
    status = mapEnumValue(status, TransactionStatusView.UNKNOWN),
    metadata = metadata?.toView(),
)

internal fun TransactionMetadataModel.toView() = TransactionMetadataView(
    location = location,
    logoURL = logoURL,
    pending = pending,
    categories = categories,
    paymentProcessor = paymentProcessor,
    merchantName = merchantName,
)

internal fun TransactionCategoryModel.toView() = TransactionCategoryView(
    name = name,
    id = id,
)

internal fun UpdateTransactionView.toDto() = UpdateTransactionDTO(
    id = id,
    notes = notes,
    payee = payee,
    date = date,
    category = category?.toDto()
)

internal fun TransactionCategoryView.toDto() = UpdateTransactionCategoryDTO(
    name = name,
    id = id
)
