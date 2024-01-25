package com.rodrigolmti.lunch.money.companion.features.home.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class HomeView(
    val overviews: List<AssetOverviewView>,
    val summary: PeriodSummaryView,
)