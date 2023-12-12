package com.rodrigolmti.lunch.money.ui.features.transactions.data.model

data class AssetModel(
    val id: Int,
    val typeName: String,
    val subtypeName: String,
    val name: String,
    val balance: String,
    val balanceAsOf: String,
    val currency: String,
    val institutionName: String?,
    val status: AssetStatus,
) {

    fun display() = "$name ($id)"
}