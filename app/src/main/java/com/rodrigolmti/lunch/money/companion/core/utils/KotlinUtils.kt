package com.rodrigolmti.lunch.money.companion.core.utils

inline fun <reified T : Enum<T>, reified R : Enum<R>> mapEnumValue(value: T, default: R): R {
    return try {
        enumValueOf<R>(value.name)
    } catch (e: IllegalArgumentException) {
        default
    }
}