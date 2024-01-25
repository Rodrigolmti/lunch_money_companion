package com.rodrigolmti.lunch.money.companion.features.home.ui

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal interface IHomeUIModel {
    val viewState: StateFlow<HomeUiState>

    fun getAccountOverview(start: Date, end: Date)
    fun onRefresh(start: Date, end: Date)
}