package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.core.onFailure
import com.rodrigolmti.lunch.money.core.onSuccess
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.ui.components.LunchLoading
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionStatus
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.BodyBold
import com.rodrigolmti.lunch.money.ui.theme.ContentAuxiliar
import com.rodrigolmti.lunch.money.ui.theme.ContentDefault
import com.rodrigolmti.lunch.money.ui.theme.Green
import com.rodrigolmti.lunch.money.ui.theme.NavyBlue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TransactionsUiState {
    data object Idle : TransactionsUiState()
    data object Loading : TransactionsUiState()
    data object Error : TransactionsUiState()
    data class Success(val transactions: List<TransactionModel>) : TransactionsUiState()
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
    val viewState by uiModel.viewState.collectAsState()

    Scaffold {

        when (viewState) {
            TransactionsUiState.Idle -> {
                // no-op
            }

            TransactionsUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            TransactionsUiState.Error -> {
                // no-op
            }

            is TransactionsUiState.Success -> {
                val transactions = (viewState as TransactionsUiState.Success).transactions

                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(transactions.size) { index ->

                        val transaction = transactions[index]

                        Card(
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = ContentDefault
                            ),
                            border = BorderStroke(
                                width = Dp.Hairline,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Text(
                                        text = transaction.payee,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 2,

                                        color = Color.White,
                                        style = Body,
                                    )

                                    HorizontalSpacer(8.dp)

                                    Text(
                                        text = formatCurrency(
                                            transaction.amount,
                                            transaction.currency
                                        ),
                                        color = Color.White,
                                        style = BodyBold,
                                    )
                                }

                                VerticalSpacer(height = 8.dp)

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Text(
                                        text = transaction.date,
                                        color = Color.White,
                                        style = Body,
                                    )

                                    HorizontalSpacer(8.dp)

                                    Text(
                                        text = getTransactionStatusLabel(transaction.status),
                                        color = getTransactionStatusColor(transaction.status),
                                        style = Body,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getTransactionStatusColor(status: TransactionStatus): Color = when (status) {
    TransactionStatus.CLEARED -> Green
    TransactionStatus.RECURRING -> NavyBlue
    TransactionStatus.RECURRING_SUGGESTED -> NavyBlue
    TransactionStatus.PENDING -> Color.Red
    TransactionStatus.UNKNOWN -> ContentAuxiliar
}

private fun getTransactionStatusLabel(status: TransactionStatus): String = when (status) {
    TransactionStatus.CLEARED -> "Cleared"
    TransactionStatus.RECURRING -> "Recurring"
    TransactionStatus.RECURRING_SUGGESTED -> "Recurring Suggested"
    TransactionStatus.PENDING -> "Pending"
    TransactionStatus.UNKNOWN -> "Unknown"
}