@file:OptIn(ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.analyze.FilterBottomSheet
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.FilterState
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.DashedDivider
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.modal.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import kotlinx.coroutines.launch

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun TransactionsSummaryScreen(
    uiModel: TransactionsSummaryUIModel = DummyITransactionSummaryUIModel(),
    onBackClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    val modalState = remember {
        mutableStateOf<TransactionSummaryModalState>(TransactionSummaryModalState.Idle)
    }

    var filterState by remember { mutableStateOf(FilterState()) }

    when (viewState) {
        TransactionsSummaryUIState.Error -> {
            LaunchedEffect(Unit) {
                modalState.value = TransactionSummaryModalState.ErrorModal
                scope.launch { sheetState.show() }
            }
        }

        TransactionsSummaryUIState.Loading -> {}
        is TransactionsSummaryUIState.Success -> {}
    }

    Scaffold(
        topBar = {
            LunchAppBar(
                stringResource(R.string.transaction_summary_title),
                onBackClick = onBackClick,
            ) {
                IconButton(onClick = {
                    modalState.value = TransactionSummaryModalState.FilterModal
                    scope.launch { sheetState.show() }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        modifier = Modifier.size(CompanionTheme.spacings.spacingE),
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
                when (modalState.value) {
                    TransactionSummaryModalState.ErrorModal -> {
                        BottomSheetComponent(
                            stringResource(R.string.transaction_modal_error_title),
                            stringResource(R.string.transaction_modal_error_message),
                        ) {
                            scope.launch { sheetState.hide() }
                        }
                    }

                    TransactionSummaryModalState.FilterModal -> {
                        FilterBottomSheet(
                            label = filterState.getDisplay(),
                            selected = filterState.preset,
                            onFilterSelected = {
                                filterState = filterState.copy(preset = it)
                            },
                            onNextMonthClick = {
                                filterState = filterState.decrease()
                            },
                            onPreviousMonthClick = {
                                filterState = filterState.increase()
                            },
                            onFilter = {
                                scope.launch { sheetState.hide() }
                                val (start, end) = filterState.getFilter()
                                uiModel.getSummary(start, end)
                            }
                        )
                    }

                    TransactionSummaryModalState.Idle -> {
                        // no-op
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            when (viewState) {
                TransactionsSummaryUIState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                TransactionsSummaryUIState.Error -> {
                    EmptyState(
                        stringResource(R.string.transaction_summary_error_label),
                    )
                }

                is TransactionsSummaryUIState.Success -> {

                    val view = (viewState as TransactionsSummaryUIState.Success).view

                    Column(modifier = Modifier.verticalScroll(scrollState)) {
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
                                .padding(
                                    start = CompanionTheme.spacings.spacingD,
                                    end = CompanionTheme.spacings.spacingD,
                                    top = CompanionTheme.spacings.spacingJ,
                                    bottom = CompanionTheme.spacings.spacingD
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(CompanionTheme.spacings.spacingD),
                            ) {
                                Text(
                                    stringResource(R.string.transaction_summary_card_title),
                                    color = White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = CompanionTheme.typography.body
                                )
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                Divider()
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                Text(
                                    stringResource(R.string.transaction_summary_income_label),
                                    color = White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = CompanionTheme.typography.bodyBold
                                )
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                view.income.forEach {
                                    CurrencyTileItem(
                                        it.key,
                                        value = it.value,
                                        labelColor = SunburstGold,
                                        currency = view.currency,
                                        valueColor = EmeraldSpring
                                    )
                                    VerticalSpacer(height = CompanionTheme.spacings.spacingA)
                                }
                                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                                DashedDivider()
                                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                                CurrencyTileItem(
                                    stringResource(R.string.transaction_summary_income_total_label),
                                    value = view.totalIncome,
                                    currency = view.currency
                                )
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                Divider()
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                Text(
                                    stringResource(R.string.transaction_summary_expenses_label),
                                    color = White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = CompanionTheme.typography.bodyBold
                                )
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                view.expense.forEach {
                                    CurrencyTileItem(
                                        it.key,
                                        value = it.value,
                                        labelColor = SunburstGold,
                                        currency = view.currency,
                                        valueColor = White
                                    )
                                    VerticalSpacer(height = CompanionTheme.spacings.spacingA)
                                }
                                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                                DashedDivider()
                                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                                CurrencyTileItem(
                                    stringResource(R.string.transaction_summary_expenses_total_label),
                                    value = view.totalExpense,
                                    currency = view.currency,
                                    valueColor = White
                                )
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                Divider()
                                VerticalSpacer(height = CompanionTheme.spacings.spacingB)
                                CurrencyTileItem(
                                    stringResource(R.string.transaction_summary_net_label),
                                    value = view.net,
                                    currency = view.currency
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrencyTileItem(
    label: String,
    labelColor: Color = White,
    value: Float,
    currency: String,
    valueColor: Color = EmeraldSpring,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            label,
            color = labelColor,
            modifier = Modifier.weight(1f),
            style = CompanionTheme.typography.bodyBold
        )
        Text(
            formatCurrency(
                value,
                currency
            ),
            color = valueColor,
            style = CompanionTheme.typography.body
        )
    }
}

@ExperimentalMaterialApi
@Composable
@LunchMoneyPreview
private fun TransactionsSummaryScreenPreview(
    @PreviewParameter(TransactionSummaryUIModelProvider::class) uiModel: TransactionsSummaryUIModel
) {
    CompanionTheme {
        TransactionsSummaryScreen(uiModel)
    }
}