package com.rodrigolmti.lunch.money.companion.composition.data.model.dto

@JvmInline
value class TokenDTO(val value: String) {
    fun format() = "Bearer $value"
}