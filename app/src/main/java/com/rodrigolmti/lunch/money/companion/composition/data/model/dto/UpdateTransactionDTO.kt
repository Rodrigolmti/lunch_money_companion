package com.rodrigolmti.lunch.money.companion.composition.data.model.dto

data class UpdateTransactionDTO(
    val id: Long,
    val notes: String? = null,
    val payee: String,
    val date: String,
    val category: UpdateTransactionCategoryDTO? = null
)

data class UpdateTransactionCategoryDTO(
    val id: Int,
    val name: String
)