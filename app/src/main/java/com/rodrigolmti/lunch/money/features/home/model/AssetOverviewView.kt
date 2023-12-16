package com.rodrigolmti.lunch.money.features.home.model

data class AssetOverviewView(
    val total: Double,
    val type: AssetTypeView,
    val assets: List<AssetModelView>
)

