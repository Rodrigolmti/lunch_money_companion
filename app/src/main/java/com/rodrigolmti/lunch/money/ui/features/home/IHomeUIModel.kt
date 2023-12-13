package com.rodrigolmti.lunch.money.ui.features.home

import kotlinx.coroutines.flow.StateFlow

interface IHomeUIModel {
    val viewState: StateFlow<HomeUiState>

    fun getAccountOverview()
}