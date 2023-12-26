package com.rodrigolmti.lunch.money.companion.features.home.ui

import kotlinx.coroutines.flow.StateFlow

internal interface IHomeUIModel {
    val viewState: StateFlow<HomeUiState>

    fun getAccountOverview()
    fun onRefresh()
}