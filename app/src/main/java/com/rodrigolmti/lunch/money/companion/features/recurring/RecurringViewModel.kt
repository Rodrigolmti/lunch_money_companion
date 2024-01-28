package com.rodrigolmti.lunch.money.companion.features.recurring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal typealias GetRecurringData = suspend () -> Outcome<List<RecurringView>, LunchError>

internal abstract class IRecurringViewModel : ViewModel(), IRecurringUIModel

internal class RecurringViewModel(
    private val getRecurring: GetRecurringData,
) : IRecurringViewModel() {

    private val _viewState = MutableStateFlow<RecurringUiState>(RecurringUiState.Loading)
    override val viewState: StateFlow<RecurringUiState> = _viewState

    init {
        getRecurringData()
    }

    override fun getRecurringData() {
        viewModelScope.launch {
            _viewState.update { RecurringUiState.Loading }
            getRecurring().onSuccess { result ->
                _viewState.update {
                    RecurringUiState.Success(result.toImmutableList())
                }
            }.onFailure {
                _viewState.update { RecurringUiState.Error }
            }
        }
    }
}