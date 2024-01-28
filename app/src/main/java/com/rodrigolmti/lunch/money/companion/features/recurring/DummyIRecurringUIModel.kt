package com.rodrigolmti.lunch.money.companion.features.recurring

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class DummyIRecurringUIModel(state: RecurringUiState = RecurringUiState.Loading) :
    IRecurringUIModel {
    override val viewState: StateFlow<RecurringUiState> = MutableStateFlow(state)

    override fun getRecurringData() {
        // no-op
    }
}