package com.rodrigolmti.lunch.money.companion.core

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal const val DEFAULT_EMPTY_STRING = ""

class SharedPreferencesDelegateFactory(private val sharedPreferences: SharedPreferences) {

    fun <T> create(default: T, key: String): ReadWriteProperty<Any?, T> {
        return SharedPreferencesDelegate(sharedPreferences, default, key)
    }
}

internal class SharedPreferencesDelegate<T>(
    private val prefs: SharedPreferences,
    private val default: T,
    private val key: String,
) : ReadWriteProperty<Any?, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is String -> prefs.getString(key, default)
            is Int -> prefs.getInt(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is Float -> prefs.getFloat(key, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
            apply()
        }
    }
}