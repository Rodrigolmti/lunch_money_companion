package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionDetailView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.UpdateTransactionView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal abstract class ITransactionDetailViewModel : ViewModel(), ITransactionDetailUIModel

internal typealias GetUserTransaction = suspend (id: Long) -> Outcome<TransactionDetailView, LunchError>

internal typealias UpdateUserTransaction = suspend (model: UpdateTransactionView) -> Outcome<Unit, LunchError>

internal class TransactionDetailViewModel(
    private val updateUserTransaction: UpdateUserTransaction,
    private val getUserTransactions: GetUserTransaction
) : ITransactionDetailViewModel() {

    private val _viewState =
        MutableStateFlow<TransactionDetailUiState>(TransactionDetailUiState.Loading)
    override val viewState: StateFlow<TransactionDetailUiState> = _viewState

    override fun getTransaction(id: Long) {
        viewModelScope.launch {
            _viewState.update { TransactionDetailUiState.Loading }
            getUserTransactions(id).onSuccess { result ->
                _viewState.update {
                    TransactionDetailUiState.Success(result)
                }
            }.onFailure {
                _viewState.update {
                    TransactionDetailUiState.Error
                }
            }
        }
    }

    override fun updateTransaction(update: UpdateTransactionView, model: TransactionDetailView) {
        viewModelScope.launch {
            _viewState.update { TransactionDetailUiState.Loading }
            updateUserTransaction(update).onSuccess {
                _viewState.update {
                    TransactionDetailUiState.Success(
                        updated = true,
                        transaction = model.copy(
                            transaction = model.transaction.copy(
                                category = update.category,
                                notes = update.notes,
                                payee = update.payee,
                                date = update.date
                            )
                        )
                    )
                }
            }.onFailure {
                _viewState.update {
                    TransactionDetailUiState.Error
                }
            }
        }
    }
}