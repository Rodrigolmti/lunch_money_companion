package com.rodrigolmti.lunch.money.companion.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date.time)
}

fun String.toDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.parse(this)
}

fun Date.formatToMonthYear(): String {
    val format = SimpleDateFormat("MMMM - yyyy", Locale.getDefault())
    return format.format(this.time)
}