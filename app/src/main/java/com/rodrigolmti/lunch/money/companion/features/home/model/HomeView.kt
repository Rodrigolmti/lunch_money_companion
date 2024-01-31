package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class HomeView(
    val overviews: ImmutableList<AssetOverviewView>,
    val summary: PeriodSummaryView,
    val pendingAssets: List<String>,
)