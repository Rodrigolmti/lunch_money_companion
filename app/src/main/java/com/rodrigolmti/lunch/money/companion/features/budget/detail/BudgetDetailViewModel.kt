package com.rodrigolmti.lunch.money.companion.features.budget.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

typealias GetBudget = (Int) -> BudgetView?

internal abstract class IBudgetDetailViewModel : ViewModel(), IBudgetDetailUIModel

internal class BudgetDetailViewModel(
    private val getBudgetLambda: GetBudget,
) : IBudgetDetailViewModel() {

    private val _viewState = MutableStateFlow<BudgetDetailUiState>(BudgetDetailUiState.Loading)
    override val viewState: StateFlow<BudgetDetailUiState> = _viewState

    override fun getBudget(budgetId: Int) {
        viewModelScope.launch {
            _viewState.value = BudgetDetailUiState.Loading
            getBudgetLambda(budgetId)?.let {
                _viewState.value = BudgetDetailUiState.Success(it)
            } ?: run {
                _viewState.value = BudgetDetailUiState.Error
            }
        }
    }
}