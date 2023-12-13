@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.ErrorBottomSheet
import com.rodrigolmti.lunch.money.ui.components.LunchAppBar
import com.rodrigolmti.lunch.money.ui.components.LunchLoading
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow

private object DummyITransactionsUIModel : ITransactionsUIModel {
    override val viewState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Loading)
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

    var showDetailBottomSheet by remember { mutableStateOf(false) }
    val detailSheetState = rememberModalBottomSheetState()

    var showErrorBottomSheet by remember { mutableStateOf(false) }
    val errorSheetState = rememberModalBottomSheetState()

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var selectedTransaction: TransactionModel? = null

    Scaffold {

        when {
            showDetailBottomSheet && selectedTransaction != null -> {
                TransactionDetailBottomSheet(
                    sheetState = detailSheetState,
                    scope = scope,
                    transaction = selectedTransaction!!,
                    onBottomSheetDismissed = {
                        showDetailBottomSheet = false
                        selectedTransaction = null
                    }
                )
            }

            showErrorBottomSheet -> {
                ErrorBottomSheet(
                    sheetState = errorSheetState,
                    scope = scope,
                    title = stringResource(R.string.common_error_title),
                    message = stringResource(R.string.transaction_error_message),
                ) {
                    showErrorBottomSheet = false
                }
            }
        }

        when (viewState) {
            TransactionsUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            TransactionsUiState.Error -> {

                showErrorBottomSheet = true

                BuildErrorState()
            }

            is TransactionsUiState.Success -> {
                val transactions = (viewState as TransactionsUiState.Success).transactions

                BuildSuccessState(
                    listState,
                    transactions,
                ) { transaction ->
                    selectedTransaction = transaction
                    showDetailBottomSheet = true
                }
            }
        }
    }
}

@Composable
private fun BuildSuccessState(
    listState: LazyListState,
    transactions: List<TransactionModel>,
    onTransactionSelected: (TransactionModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
    ) {

        LunchAppBar(stringResource(R.string.transactions_title))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                ),
            state = listState,
        ) {
            items(transactions.size) { index ->

                val transaction = transactions[index]

                TransactionItem(transaction) {
                    onTransactionSelected(transaction)
                }
            }
        }
    }
}

@Composable
private fun BuildErrorState() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        LunchAppBar(stringResource(R.string.transactions_title))

        Center(
            Modifier.padding(
                start = 16.dp,
                end = 16.dp,
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_white_coin),
                contentDescription = "White Coin Image",
            )

            VerticalSpacer(height = 16.dp)

            Text(
                text = stringResource(R.string.transaction_empty_content_message),
                style = Body,
                color = White,
            )
        }
    }
}