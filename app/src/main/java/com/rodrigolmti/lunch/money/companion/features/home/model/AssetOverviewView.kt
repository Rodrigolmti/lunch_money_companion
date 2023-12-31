package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator

@Immutable
internal data class AssetOverviewView(
    val total: Double,
    val type: AssetTypeView,
    val assets: List<AssetModelView>
)

internal fun fakeAssetOverviewView() = AssetOverviewView(
    total = ValueGenerator.gen(),
    type = AssetTypeView.entries.toTypedArray().random(),
    assets = listOf(
        fakeAssetModelView(),
        fakeAssetModelView(),
        fakeAssetModelView(),
    )
)
