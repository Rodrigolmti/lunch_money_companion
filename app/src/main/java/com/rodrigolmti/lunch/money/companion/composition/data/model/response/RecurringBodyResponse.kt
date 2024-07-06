package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal class RecurringBodyResponse(
    @SerialName("recurring_expenses")
    val expenses: List<RecurringResponse>,
)

@Serializable
internal data class RecurringResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("end_date")
    val endDate: String?,
    @SerialName("cadence")
    val cadence: String,
    @SerialName("payee")
    val payee: String,
    @SerialName("amount")
    val amount: Float,
    @SerialName("currency")
    val currency: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("billing_date")
    val billingDate: String,
    @SerialName("original_name")
    val originalName: String? = null,
)