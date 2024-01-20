package com.rodrigolmti.lunch.money.companion.features.budget

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
import java.util.Date

typealias GetBudgetData = suspend (start: Date, end: Date) -> Outcome<List<BudgetView>, LunchError>

internal abstract class IBudgetViewModel : ViewModel(), IBudgetUIModel

internal class BudgetViewModel(
    private val getBudget: GetBudgetData,
) : IBudgetViewModel() {

    private val _viewState = MutableStateFlow<BudgetUiState>(BudgetUiState.Loading)
    override val viewState: StateFlow<BudgetUiState> = _viewState

    override fun getBudgetData(start: Date, end: Date) {
        viewModelScope.launch {
            _viewState.update { BudgetUiState.Loading }
            getBudget(start, end).onSuccess { result ->
                _viewState.update {
                    BudgetUiState.Success(result.toImmutableList())
                }
            }.onFailure {
                _viewState.update { BudgetUiState.Error }
            }
        }
    }
}