package com.rodrigolmti.lunch.money.composition.data.repository

@JvmInline
value class Token(private val value: String) {
    fun format() = "Bearer $value"
}