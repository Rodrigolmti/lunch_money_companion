package com.rodrigolmti.lunch.money.composition.data.model.model

@JvmInline
value class Token(val value: String) {
    fun format() = "Bearer $value"
}