package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal data class RecurringModel(
    val id: Int,
    val endDate: String?,
    val cadence: String,
    val payee: String,
    val amount: Float,
    val currency: String,
    val description: String?,
    val billingDate: String,
    val originalName: String?,
)