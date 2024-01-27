@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.uikit.components.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import kotlinx.coroutines.launch

sealed class TransactionsDetailErrorState {
    data object Idle : TransactionsDetailErrorState()
    data object GetTransactionError : TransactionsDetailErrorState()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun TransactionsDetailScreen(
    uiModel: ITransactionDetailUIModel = DummyITransactionDetailUIModel(),
    id: Int = 0,
    onBackClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    val errorState = remember {
        mutableStateOf<TransactionsDetailErrorState>(TransactionsDetailErrorState.Idle)
    }

    LaunchedEffect(Unit) {
        uiModel.getTransaction(id)
    }

    when (viewState) {
        TransactionDetailUiState.Error -> {
            LaunchedEffect(Unit) {
                errorState.value = TransactionsDetailErrorState.GetTransactionError
                scope.launch { sheetState.show() }
            }
        }
        TransactionDetailUiState.Loading -> {}
        is TransactionDetailUiState.Success -> {}
    }

    Scaffold(
        topBar = {
            LunchAppBar(
                stringResource(R.string.transaction_title),
                onBackClick = onBackClick,
            )
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                BottomSheetComponent(
                    stringResource(R.string.transaction_modal_error_title),
                    stringResource(R.string.transaction_modal_error_message),
                ) {
                    scope.launch { sheetState.hide() }
                }
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            when (viewState) {
                TransactionDetailUiState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                TransactionDetailUiState.Error -> {
                    EmptyState(
                        stringResource(R.string.transaction_error_message),
                    )
                }

                is TransactionDetailUiState.Success -> {
                    val transaction = (viewState as TransactionDetailUiState.Success).transaction

                    BuildSuccessState(transaction)
                }
            }
        }
    }
}

@Composable
private fun BuildSuccessState(transaction: TransactionView) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(
                top = CompanionTheme.spacings.spacingJ,
                start = CompanionTheme.spacings.spacingD,
                end = CompanionTheme.spacings.spacingD,
                bottom = CompanionTheme.spacings.spacingD,
            ),
        verticalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingD)
    ) {
        LunchTextField(
            label = stringResource(R.string.transaction_date_label),
            readOnly = true,
            text = transaction.date,
        )
        LunchTextField(
            label = stringResource(R.string.transaction_category_label),
            readOnly = true,
            text = transaction.categoryName ?: "-",
        )
        LunchTextField(
            label = stringResource(R.string.transaction_payee_label),
            readOnly = true,
            text = transaction.payee,
        )
        Text(
            text = stringResource(R.string.transaction_amount_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = formatCurrency(
                transaction.amount,
                transaction.currency
            ),
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        LunchTextField(
            label = stringResource(R.string.transaction_notes_label),
            readOnly = true,
            text = transaction.notes ?: "-",
        )
        Text(
            text = stringResource(R.string.transaction_original_name_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = transaction.originalName ?: "-",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        Text(
            text = stringResource(R.string.transaction_paid_from_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = transaction.assetName ?: "-",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
    }
}

@Composable
@LunchMoneyPreview
private fun TransactionsScreenPreview(
    @PreviewParameter(TransactionDetailUIModelProvider::class) uiModel: ITransactionDetailUIModel
) {
    CompanionTheme {
        TransactionsDetailScreen(uiModel)
    }
}
