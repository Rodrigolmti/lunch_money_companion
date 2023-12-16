package com.rodrigolmti.lunch.money.features.home.ui

import kotlinx.coroutines.flow.StateFlow

interface IHomeUIModel {
    val viewState: StateFlow<HomeUiState>

    fun getAccountOverview()
    fun onRefresh()
}