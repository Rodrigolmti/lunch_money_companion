@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun TransactionsScreen(
    uiModel: ITransactionsUIModel = DummyITransactionsUIModel(),
    onTransactionItemClick: (TransactionView) -> Unit = {},
    onError: (String, String) -> Unit = { _, _ -> },
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    var filterState by remember { mutableStateOf(FilterState()) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        searchTransactions(filterState, uiModel)
    }

    Scaffold(
        topBar = {
            LunchAppBar(stringResource(R.string.transactions_title)) {
                IconButton(onClick = {
                    scope.launch { sheetState.show() }
                }) {
                    Icon(
                        Icons.Filled.List,
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
                IconButton(onClick = {
                    searchTransactions(filterState, uiModel)
                }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
            }
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                TransactionFilterBottomSheet(
                    label = filterState.getDisplay(),
                    onNextMonthClick = {
                        filterState = filterState.decrease()
                    },
                    onPreviousMonthClick = {
                        filterState = filterState.increase()
                    },
                    onFilter = {
                        scope.launch { sheetState.hide() }
                        searchTransactions(filterState, uiModel)
                    }
                )
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            when (viewState) {
                TransactionsUiState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                TransactionsUiState.Error -> {
                    EmptyState(
                        stringResource(R.string.transaction_empty_content_message),
                    )

                    onError(
                        stringResource(R.string.common_error_title),
                        stringResource(R.string.transaction_error_message)
                    )
                }

                is TransactionsUiState.Success -> {
                    val transactions = (viewState as TransactionsUiState.Success).transactions

                    if (transactions.isEmpty()) {
                        EmptyState(
                            stringResource(R.string.transaction_empty_content_message),
                        )
                    }

                    BuildSuccessState(
                        listState,
                        transactions,
                    ) { transaction ->
                        onTransactionItemClick(transaction)
                    }
                }
            }
        }
    }
}

private fun searchTransactions(
    filterState: FilterState,
    uiModel: ITransactionsUIModel
) {
    val (start, end) = filterState.getFilter()
    uiModel.getTransactions(start, end)
}

@Composable
private fun BuildSuccessState(
    listState: LazyListState,
    transactions: List<TransactionView>,
    onTransactionSelected: (TransactionView) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 64.dp)
    ) {
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
            items(
                count = transactions.size,
                key = { index -> transactions[index].id },
            ) { index ->

                val transaction = transactions[index]

                TransactionItem(transaction) {
                    onTransactionSelected(transaction)
                }
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun TransactionsScreenPreview(
    @PreviewParameter(TransactionsUIModelProvider::class) uiModel: ITransactionsUIModel
) {
    TransactionsScreen(uiModel)
}
