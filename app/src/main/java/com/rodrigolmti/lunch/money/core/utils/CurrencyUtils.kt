package com.rodrigolmti.lunch.money.core.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatCurrency(value: Float, currencyCode: String): String {
    val locale = Locale.getDefault()
    val format = NumberFormat.getCurrencyInstance(locale).apply {
        currency = Currency.getInstance(currencyCode)
    }
    return format.format(value)
}