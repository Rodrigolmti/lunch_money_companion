package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal abstract class ITransactionsViewModel : ViewModel(), ITransactionsUIModel

internal typealias GetUserTransactions = suspend () -> Outcome<List<TransactionView>, LunchError>

internal class TransactionsViewModel(
    private val getUserTransactions: GetUserTransactions
) : ITransactionsViewModel() {

    private val _viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Loading)
    override val viewState: StateFlow<TransactionsUiState> = _viewState

    init {
        getTransactions()
    }

    override fun getTransactions() {
        viewModelScope.launch {
            _viewState.value = TransactionsUiState.Loading
            getUserTransactions().onSuccess {
                _viewState.value = TransactionsUiState.Success(it.toImmutableList())
            }.onFailure {
                _viewState.value = TransactionsUiState.Error
            }
        }
    }
}