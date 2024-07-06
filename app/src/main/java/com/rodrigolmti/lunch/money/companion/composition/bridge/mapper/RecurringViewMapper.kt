package com.rodrigolmti.lunch.money.companion.composition.bridge.mapper

import com.rodrigolmti.lunch.money.companion.composition.domain.model.RecurringModel
import com.rodrigolmti.lunch.money.companion.features.recurring.RecurringView

/**
 * * -1 Multiplication is due to values being returned on the wrong scale;
 * Income have - and expenses have +.
 */

internal fun RecurringModel.toView(): RecurringView {
    return RecurringView(
        cadence = cadence,
        payee = payee,
        amount = amount * -1,
        currency = currency,
        description = description,
        billingDate = billingDate,
    )
}