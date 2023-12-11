package com.rodrigolmti.lunch.money.composition.data.model.dto

@JvmInline
value class TokenDTO(val value: String) {
    fun format() = "Bearer $value"
}