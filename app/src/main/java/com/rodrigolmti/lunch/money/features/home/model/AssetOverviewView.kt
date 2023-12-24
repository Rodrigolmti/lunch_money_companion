package com.rodrigolmti.lunch.money.features.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class AssetOverviewView(
    val total: Double,
    val type: AssetTypeView,
    val assets: List<AssetModelView>
)

