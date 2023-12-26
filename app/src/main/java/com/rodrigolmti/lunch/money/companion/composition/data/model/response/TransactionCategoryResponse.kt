package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionCategoryBodyResponse(
    @SerialName("categories")
    val categories: List<TransactionCategoryResponse>,
)

@Serializable
internal data class TransactionCategoryResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("is_income")
    val isIncome: Boolean,
    @SerialName("exclude_from_budget")
    val excludeFromBudget: Boolean,
    @SerialName("exclude_from_totals")
    val excludeFromTotals: Boolean,
)