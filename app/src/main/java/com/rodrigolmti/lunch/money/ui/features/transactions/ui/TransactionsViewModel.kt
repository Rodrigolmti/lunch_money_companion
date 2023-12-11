package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.onFailure
import com.rodrigolmti.lunch.money.core.onSuccess
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class ITransactionsViewModel : ViewModel(), ITransactionsUIModel

class TransactionsViewModel(
    private val getUserTransactions: suspend () -> Outcome<List<TransactionModel>, LunchError>
) : ITransactionsViewModel() {

    private val _viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Idle)
    override val viewState: StateFlow<TransactionsUiState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = TransactionsUiState.Loading

            getUserTransactions()
                .onSuccess {
                    _viewState.value = TransactionsUiState.Success(it)
                }
                .onFailure {
                    _viewState.value = TransactionsUiState.Error
                }
        }
    }
}