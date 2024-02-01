package com.rodrigolmti.lunch.money.companion.composition.domain.usecase

import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel

internal interface GetTransactionSumByCategoryUseCase {
    suspend operator fun invoke(transactions: List<TransactionModel>): Map<String, Float>
}

internal class GetTransactionSumByCategory : GetTransactionSumByCategoryUseCase {
    override suspend fun invoke(transactions: List<TransactionModel>): Map<String, Float> {
        val map = mutableMapOf<String, Float>()
        transactions.forEach {
            val key = it.category?.name ?: "uncategorized"
            val value = map[key] ?: 0.0f
            map[key] = value + it.amount
        }
        return map
    }
}