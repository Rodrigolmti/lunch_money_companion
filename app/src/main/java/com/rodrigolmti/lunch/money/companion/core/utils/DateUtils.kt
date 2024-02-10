package com.rodrigolmti.lunch.money.companion.core.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
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

// Convert a date in long millis to a formatted string date
fun Long.toFormattedDate(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(this)
}

fun getCurrentMonthDates(date: Calendar = Calendar.getInstance()): Pair<Date, Date> {
    val startOfMonth = date.clone() as Calendar
    startOfMonth.set(Calendar.DAY_OF_MONTH, 1)

    val endOfMonth = date.clone() as Calendar
    endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))

    return Pair(startOfMonth.time, endOfMonth.time)
}