package com.rodrigolmti.lunch.money.companion.features.recurring

import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator
import java.util.UUID

data class RecurringView(
    val id: UUID = UUID.randomUUID(),
    val cadence: String,
    val payee: String,
    val amount: Float,
    val currency: String,
    val description: String?,
    val billingDate: String,
    val type: RecurringViewType,
)

enum class RecurringViewType {
    CLEARED, SUGGESTED
}

fun fakeRecurringView() = RecurringView(
    cadence = ValueGenerator.gen(),
    payee = ValueGenerator.gen(),
    amount = ValueGenerator.gen(),
    currency = ValueGenerator.currency(),
    description = ValueGenerator.gen(),
    billingDate = ValueGenerator.gen(),
    type = ValueGenerator.gen(),
    id = ValueGenerator.gen(),
)