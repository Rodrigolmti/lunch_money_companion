package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class AssetOverviewView(
    val total: Double,
    val type: AssetTypeView,
    val assets: List<AssetModelView>
)

