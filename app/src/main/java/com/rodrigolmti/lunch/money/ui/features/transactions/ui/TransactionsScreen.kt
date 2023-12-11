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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.ui.components.LunchLoading
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionStatus
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.BodyBold
import com.rodrigolmti.lunch.money.ui.theme.CharcoalMist
import com.rodrigolmti.lunch.money.ui.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.ui.theme.SilverLining
import com.rodrigolmti.lunch.money.ui.theme.TropicalLagoon
import com.rodrigolmti.lunch.money.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow

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

                        TransactionItem(transaction)
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(transaction: TransactionModel) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist
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

                    color = White,
                    style = Body,
                )

                HorizontalSpacer(8.dp)

                Text(
                    text = formatCurrency(
                        transaction.amount,
                        transaction.currency
                    ),
                    color = White,
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
                    color = White,
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

private fun getTransactionStatusColor(status: TransactionStatus): Color = when (status) {
    TransactionStatus.CLEARED -> EmeraldSpring
    TransactionStatus.RECURRING -> TropicalLagoon
    TransactionStatus.RECURRING_SUGGESTED -> TropicalLagoon
    TransactionStatus.PENDING -> Color.Red
    TransactionStatus.UNKNOWN -> SilverLining
}

@Composable
private fun getTransactionStatusLabel(status: TransactionStatus): String = when (status) {
    TransactionStatus.CLEARED -> stringResource(R.string.transaction_label_cleared)
    TransactionStatus.RECURRING -> stringResource(R.string.transaction_label_recurring)
    TransactionStatus.RECURRING_SUGGESTED -> stringResource(R.string.transaction_label_recurring_suggested)
    TransactionStatus.PENDING -> stringResource(R.string.transaction_label_pending)
    TransactionStatus.UNKNOWN -> stringResource(R.string.transaction_label_unknown)
}