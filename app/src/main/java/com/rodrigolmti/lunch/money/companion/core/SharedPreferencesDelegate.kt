package com.rodrigolmti.lunch.money.companion.core

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal const val DEFAULT_EMPTY_STRING = ""

class SharedPreferencesDelegateFactory(private val sharedPreferences: SharedPreferences) {

    fun <T> create(default: T, key: String): ReadWriteProperty<Any?, T> {
        return SharedPreferencesDelegate(sharedPreferences, default, key)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}

internal class SharedPreferencesDelegate<T>(
    private val sharedPreferences: SharedPreferences,
    private val default: T,
    private val key: String,
) : ReadWriteProperty<Any?, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (default) {
            is Long -> sharedPreferences.getLong(key, default)
            is String -> sharedPreferences.getString(key, default)
            is Int -> sharedPreferences.getInt(key, default)
            is Boolean -> sharedPreferences.getBoolean(key, default)
            is Float -> sharedPreferences.getFloat(key, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(sharedPreferences.edit()) {
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