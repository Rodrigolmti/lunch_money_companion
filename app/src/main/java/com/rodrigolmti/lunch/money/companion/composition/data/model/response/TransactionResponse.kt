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
    val id: Int,
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
    @SerialName("is_group")
    val isGroup: Boolean,
    @SerialName("group_id")
    val groupId: Int? = null,
    @SerialName("parent_id")
    val parentId: Int? = null,
    @SerialName("external_id")
    val externalId: Int? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("subtype")
    val subtype: String? = null,
    @SerialName("fees")
    val fees: String? = null,
    @SerialName("price")
    val price: String? = null,
    @SerialName("quantity")
    val quantity: String? = null
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