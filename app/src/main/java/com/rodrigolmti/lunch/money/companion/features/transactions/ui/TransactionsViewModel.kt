package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.core.utils.getCurrentMonthDates
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

internal abstract class ITransactionsViewModel : ViewModel(), ITransactionsUIModel

internal typealias GetUserTransactions = suspend (
    start: Date, end: Date
) -> Outcome<List<TransactionView>, LunchError>

internal class TransactionsViewModel(
    private val getUserTransactions: GetUserTransactions,
) : ITransactionsViewModel() {

    private val _viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Loading)
    override val viewState: StateFlow<TransactionsUiState> = _viewState

    init {
        val (start, end) = getCurrentMonthDates()
        getTransactions(start, end)
    }

    override fun getTransactions(start: Date, end: Date) {
        viewModelScope.launch {
            _viewState.update { TransactionsUiState.Loading }
            getUserTransactions(start, end).onSuccess { result ->
                _viewState.update {
                    TransactionsUiState.Success(result.toImmutableList())
                }
            }.onFailure {
                _viewState.update {
                    TransactionsUiState.Error
                }
            }
        }
    }
}