package com.rodrigolmti.lunch.money.companion.features.recurring

import kotlinx.collections.immutable.ImmutableList

sealed class RecurringUiState {
    data object Loading : RecurringUiState()
    data object Error : RecurringUiState()
    data class Success(val values: ImmutableList<RecurringView>) : RecurringUiState()
}