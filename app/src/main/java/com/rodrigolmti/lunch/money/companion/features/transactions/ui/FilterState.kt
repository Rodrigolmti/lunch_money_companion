package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import com.rodrigolmti.lunch.money.companion.core.utils.getCurrentMonthDates
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class FilterState(
    private val calendar: Calendar = Calendar.getInstance(),
    val preset: FilterPreset = FilterPreset.CUSTOM
) {

    fun increase(): FilterState {
        val newDate = calendar.clone() as Calendar
        newDate.add(Calendar.MONTH, 1)
        return FilterState(newDate)
    }

    fun decrease(): FilterState {
        val newDate = calendar.clone() as Calendar
        newDate.add(Calendar.MONTH, -1)
        return FilterState(newDate)
    }

    fun getFilter(): Pair<Date, Date> {
        return when (preset) {
            FilterPreset.MONTH_TO_DATE -> {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.setTimeToStartOfDay()
                val start = calendar.time
                start to Date()
            }

            FilterPreset.YEAR_TO_DATE -> {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.setTimeToStartOfDay()
                val start = calendar.time
                start to Date()
            }

            FilterPreset.LAST_7_DAYS -> {
                val calendar: Calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -6)
                val start = calendar.time
                start to Date()
            }

            FilterPreset.LAST_30_DAYS -> {
                val calendar: Calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -29)
                val start = calendar.time
                start to Date()
            }

            FilterPreset.LAST_MONTH -> {
                val calendar: Calendar = Calendar.getInstance()
                calendar.add(Calendar.MONTH, -1)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.setTimeToStartOfDay()
                val start = calendar.time
                start to Date()
            }

            FilterPreset.LAST_3_MONTHS -> {
                val calendar: Calendar = Calendar.getInstance()
                calendar.add(Calendar.MONTH, -2)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.setTimeToStartOfDay()
                val start = calendar.time
                start to Date()
            }

            FilterPreset.CUSTOM -> {
                getCurrentMonthDates(calendar)
            }
        }
    }

    fun getStartDateAsString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(getFilter().first)
    }

    fun getDisplay(): String {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}

fun Calendar.setTimeToStartOfDay() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

enum class FilterPreset {
    MONTH_TO_DATE,
    YEAR_TO_DATE,
    LAST_7_DAYS,
    LAST_30_DAYS,
    LAST_MONTH,
    LAST_3_MONTHS,
    CUSTOM,
}