package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import com.rodrigolmti.lunch.money.companion.core.utils.getCurrentMonthDates
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

    fun getFilter(): Pair<Date, Date> = getCurrentMonthDates(date)

    fun getDisplay(): String {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return dateFormat.format(date.time)
    }
}