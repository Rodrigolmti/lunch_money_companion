@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.ui.components.LunchButton
import com.rodrigolmti.lunch.money.ui.components.LunchLoading
import com.rodrigolmti.lunch.money.ui.components.LunchTextField
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionStatus
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.BodyBold
import com.rodrigolmti.lunch.money.ui.theme.CharcoalMist
import com.rodrigolmti.lunch.money.ui.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.ui.theme.Header
import com.rodrigolmti.lunch.money.ui.theme.MidnightSlate
import com.rodrigolmti.lunch.money.ui.theme.SilverLining
import com.rodrigolmti.lunch.money.ui.theme.SunburstGold
import com.rodrigolmti.lunch.money.ui.theme.TropicalLagoon
import com.rodrigolmti.lunch.money.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private object DummyITransactionsUIModel : ITransactionsUIModel {
    override val viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Idle)
    override fun getTransactions() {
        // no-op
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionsScreen(
    uiModel: ITransactionsUIModel = DummyITransactionsUIModel,
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var selectedTransaction: TransactionModel? = null

    LaunchedEffect(true) {
        uiModel.getTransactions()
    }

    Scaffold {

        if (showBottomSheet && selectedTransaction != null) {
            TransactionItemDetailBottomSheet(
                sheetState = sheetState,
                scope = scope,
                transaction = selectedTransaction!!,
                onBottomSheetDismissed = {
                    showBottomSheet = false
                }
            )
        }

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

                Column(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                        )
                ) {

                    Text(
                        text = "Transactions",
                        style = Header,
                        color = SunburstGold,
                    )

                    VerticalSpacer(height = 16.dp)

                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState,
                    ) {
                        items(transactions.size) { index ->

                            val transaction = transactions[index]

                            TransactionItem(transaction) {
                                selectedTransaction = transaction
                                showBottomSheet = true
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: TransactionModel,
    onItemClicked: () -> Unit = {},
) {
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
            .clickable {
                onItemClicked()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 8.dp,
                    bottom = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = transaction.payee,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(1f),
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

            HorizontalSpacer(width = 8.dp)

            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = SilverLining,
            )
        }
    }
}

@Composable
internal fun TransactionItemDetailBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    transaction: TransactionModel,
    onBottomSheetDismissed: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = {
            onBottomSheetDismissed()
        },
        containerColor = MidnightSlate,
        sheetState = sheetState,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                "Transaction Details",
                style = Header,
                color = White,
            )
            VerticalSpacer(height = 16.dp)
            LunchTextField(
                label = "Date",
                readOnly = true,
                text = transaction.date,
            )
            VerticalSpacer(height = 8.dp)
            LunchTextField(
                label = "Category",
                readOnly = true,
                text = transaction.category?.name ?: "-",
            )
            VerticalSpacer(height = 8.dp)
            LunchTextField(
                label = "Payee Name",
                readOnly = true,
                text = transaction.payee,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = "AMOUNT",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = Body,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = formatCurrency(
                    transaction.amount,
                    transaction.currency
                ),
                color = White,
                style = BodyBold,
            )
            VerticalSpacer(height = 8.dp)
            LunchTextField(
                label = "Notes",
                readOnly = true,
                text = transaction.notes ?: "-",
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = "ORIGINAL NAME",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = Body,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = transaction.originalName ?: "-",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = White,
                style = BodyBold,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = "PAID FROM",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = Body,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = transaction.asset?.display() ?: "-",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = White,
                style = BodyBold,
            )
            VerticalSpacer(height = 24.dp)
            LunchButton(
                label = "Close",
            ) {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onBottomSheetDismissed()
                    }
                }
            }
            VerticalSpacer(height = 32.dp)
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