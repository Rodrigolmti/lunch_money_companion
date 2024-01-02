package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FilterState(private val date: Calendar = Calendar.getInstance()) {

    fun increase(): FilterState {
        val newDate = date.clone() as Calendar
        newDate.add(Calendar.MONTH, -1)
        return FilterState(newDate)
    }

    fun decrease(): FilterState {
        val newDate = date.clone() as Calendar
        newDate.add(Calendar.MONTH, 1)
        return FilterState(newDate)
    }

    fun getFilter(): Pair<Date, Date> {
        val startOfMonth = date.clone() as Calendar
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1)

        val endOfMonth = date.clone() as Calendar
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))

        return Pair(startOfMonth.time, endOfMonth.time)
    }

    fun getDisplay(): String {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return dateFormat.format(date.time)
    }
}