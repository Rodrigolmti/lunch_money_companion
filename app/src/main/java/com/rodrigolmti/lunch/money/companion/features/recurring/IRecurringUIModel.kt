package com.rodrigolmti.lunch.money.companion.features.recurring

import kotlinx.coroutines.flow.StateFlow

internal interface IRecurringUIModel {
    val viewState: StateFlow<RecurringUiState>

    fun getRecurringData()
}