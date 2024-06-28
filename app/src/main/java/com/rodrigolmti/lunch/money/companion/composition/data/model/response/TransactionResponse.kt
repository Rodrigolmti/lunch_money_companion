package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class TransactionBodyResponse(
    @SerialName("transactions")
    val transactions: List<TransactionResponse>
)

@Keep
@Serializable
internal data class TransactionResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("is_income")
    val isIncome: Boolean? = null,
    @SerialName("exclude_from_totals")
    val excludeFromTotals: Boolean? = null,
    @SerialName("date")
    val date: String,
    @SerialName("payee")
    val payee: String,
    @SerialName("amount")
    val amount: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("to_base")
    val toBase: Double,
    @SerialName("notes")
    val notes: String? = null,
    @SerialName("category_id")
    val categoryId: Int? = null,
    @SerialName("recurring_id")
    val recurringId: Int? = null,
    @SerialName("asset_id")
    val assetId: Int? = null,
    @SerialName("plaid_account_id")
    val plaidAccountId: Int? = null,
    @SerialName("status")
    val status: TransactionStatusResponse = TransactionStatusResponse.UNKNOWN,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("subtype")
    val subtype: String? = null,
)

@Keep
@Serializable
internal data class UpdateTransactionBodyResponse(
    @SerialName("transaction")
    val transaction: UpdateTransactionResponse
)

@Keep
@Serializable
internal data class UpdateTransactionResponse(
    @SerialName("date")
    val date: String,
    @SerialName("payee")
    val payee: String,
    @SerialName("notes")
    val notes: String? = null,
    @SerialName("category_id")
    val categoryId: Int? = null,
)

@Keep
@Serializable
internal enum class TransactionStatusResponse {
    @SerialName("cleared")
    CLEARED,

    @SerialName("uncleared")
    UNCLEARED,

    @SerialName("recurring")
    RECURRING,

    @SerialName("recurring_suggested")
    RECURRING_SUGGESTED,

    @SerialName("pending")
    PENDING,

    @SerialName("delete_pending")
    DELETE_PENDING,

    UNKNOWN,
}