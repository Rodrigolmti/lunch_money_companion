package com.rodrigolmti.lunch.money.companion.composition.data.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.response.RecurringBodyResponse
import com.rodrigolmti.lunch.money.companion.composition.data.model.response.RecurringResponse
import com.rodrigolmti.lunch.money.companion.composition.domain.model.RecurringModel

internal fun mapRecurringList(recurring: RecurringBodyResponse): List<RecurringModel> {
    return recurring.expenses.map {
        it.toModel()
    }
}

internal fun RecurringResponse.toModel(): RecurringModel {
    return RecurringModel(
        currency = currency,
        amount = amount,
        billingDate = billingDate,
        cadence = cadence,
        description = description,
        endDate = endDate,
        id = id,
        originalName = originalName,
        payee = payee,
    )
}