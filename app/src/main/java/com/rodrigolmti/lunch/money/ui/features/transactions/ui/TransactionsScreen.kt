package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

sealed class TransactionsUiState {
    data object Idle : TransactionsUiState()
    data object Loading : TransactionsUiState()
    data object Error : TransactionsUiState()
    data class Success(private val transactions: List<TransactionModel>) : TransactionsUiState()
}

interface ITransactionsUIModel {
    val viewState: StateFlow<TransactionsUiState>
}

abstract class ITransactionsViewModel : ViewModel(), ITransactionsUIModel

class TransactionsViewModel(
    private val getTransactions: suspend () -> Outcome<List<TransactionModel>, LunchError>
) : ITransactionsViewModel() {

    private val _viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Idle)
    override val viewState: StateFlow<TransactionsUiState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = TransactionsUiState.Loading

            getTransactions()
                .onSuccess {
                    _viewState.value = TransactionsUiState.Success(it)
                }
                .onFailure {
                    _viewState.value = TransactionsUiState.Error
                }
        }
    }
}

private object DummyITransactionsUIModel : ITransactionsUIModel {
    override val viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Idle)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionsScreen(
    uiModel: ITransactionsUIModel = DummyITransactionsUIModel,
) {
    Scaffold {
        Text("xingu")
    }
}