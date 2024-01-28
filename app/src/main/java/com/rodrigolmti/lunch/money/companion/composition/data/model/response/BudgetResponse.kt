package com.rodrigolmti.lunch.money.companion.composition.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class BudgetResponse(
    @SerialName("category_name")
    val categoryName: String,
    @SerialName("category_id")
    val categoryId: Int? = null,
    @SerialName("category_group_name")
    val categoryGroupName: String? = null,
    @SerialName("group_id")
    val groupId: Int? = null,
    @SerialName("is_group")
    val isGroup: Boolean? = null,
    @SerialName("is_income")
    val isIncome: Boolean,
    @SerialName("exclude_from_budget")
    val excludeFromBudget: Boolean,
    @SerialName("exclude_from_totals")
    val excludeFromTotals: Boolean,
    @SerialName("data")
    val data: Map<String, CategoryResponse?>,
    @SerialName("config")
    val config: CategoryConfigResponse? = null,
    @SerialName("recurring")
    val recurring: BudgetRecurringBodyResponse? = null,
    @SerialName("order")
    val order: Int
)

@Keep
@Serializable
internal data class CategoryResponse(
    @SerialName("num_transactions")
    val numTransactions: Int? = null,
    @SerialName("spending_to_base")
    val spendingToBase: Float? = null,
    @SerialName("budget_to_base")
    val budgetToBase: Float? = null,
    @SerialName("budget_amount")
    val budgetAmount: Float? = null,
    @SerialName("budget_currency")
    val budgetCurrency: String? = null,
    @SerialName("is_automated")
    val isAutomated: Boolean? = null
)

@Keep
@Serializable
internal data class BudgetRecurringBodyResponse(
    @SerialName("list")
    val list: List<BudgetRecurringResponse>
)

@Keep
@Serializable
internal data class BudgetRecurringResponse(
    @SerialName("payee")
    val payee: String,
    @SerialName("amount")
    val amount: Float,
    @SerialName("currency")
    val currency: String,
)

@Keep
@Serializable
internal data class CategoryConfigResponse(
    @SerialName("config_id")
    val configId: Int,
    @SerialName("cadence")
    val cadence: String,
    @SerialName("amount")
    val amount: Float,
    @SerialName("currency")
    val currency: String,
    @SerialName("to_base")
    val toBase: Float,
    @SerialName("auto_suggest")
    val autoSuggest: String
)
