package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.core.utils.getCurrentMonthDates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

internal abstract class ITransactionsSummaryViewModel : TransactionsSummaryUIModel, ViewModel()

internal typealias GetTransactionSummary = suspend (
    start: Date, end: Date
) -> Outcome<TransactionSummaryView, LunchError>

internal class TransactionsSummaryViewModel(
    private val getTransactionSummary: GetTransactionSummary
) : ITransactionsSummaryViewModel() {
    private val _viewState =
        MutableStateFlow<TransactionsSummaryUIState>(TransactionsSummaryUIState.Loading)
    override val viewState: StateFlow<TransactionsSummaryUIState> = _viewState

    init {
        val (start, end) = getCurrentMonthDates()
        getSummary(start, end)
    }

    override fun getSummary(start: Date, end: Date) {
        viewModelScope.launch {
            _viewState.update { TransactionsSummaryUIState.Loading }
            getTransactionSummary(start, end).onSuccess { result ->
                _viewState.update {
                    TransactionsSummaryUIState.Success(result)
                }
            }.onFailure {
                _viewState.update {
                    TransactionsSummaryUIState.Error
                }
            }
        }
    }
}