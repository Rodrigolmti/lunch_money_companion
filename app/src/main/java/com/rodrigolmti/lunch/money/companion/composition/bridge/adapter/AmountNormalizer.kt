package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

object AmountNormalizer {

    fun normalizeAmount(amount: Float): Float {
        return if (amount < 0) {
            amount * -1
        } else {
            amount
        }
    }

    fun fixAmountBasedOnSymbol(isIncome: Boolean, amount: Float): Float {
        return if (isIncome) {
            if (amount < 0) {
                amount * -1
            } else {
                amount
            }
        } else {
            if (amount > 0) {
                amount * -1
            } else {
                amount
            }

        }
    }
}