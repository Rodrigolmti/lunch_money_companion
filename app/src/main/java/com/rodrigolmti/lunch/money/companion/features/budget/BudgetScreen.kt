@file:OptIn(ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.budget

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.FilterState
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionFilterBottomSheet
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.launch

@Composable
@LunchMoneyPreview
private fun BudgetScreenPreview(
    @PreviewParameter(BudgetUIModelProvider::class) uiModel: IBudgetUIModel
) {
    BudgetScreen(uiModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun BudgetScreen(
    uiModel: IBudgetUIModel = DummyIBudgetUIModel(),
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
        getBudget(filterState, uiModel)
    }

    Scaffold(
        topBar = {
            LunchAppBar(stringResource(R.string.budget_title)) {
                IconButton(onClick = {
                    scope.launch { sheetState.show() }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        modifier = Modifier.size(CompanionTheme.spacings.spacingE),
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
                IconButton(onClick = {
                    getBudget(filterState, uiModel)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        modifier = Modifier.size(CompanionTheme.spacings.spacingE),
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
            }
        }) {

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
                        getBudget(filterState, uiModel)
                    }
                )
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {

            when (viewState) {
                BudgetUiState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                is BudgetUiState.Success -> {
                    val budget = (viewState as BudgetUiState.Success).view

                    Column(
                        modifier = Modifier
                            .padding(top = CompanionTheme.spacings.spacingI)
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                bottom = CompanionTheme.spacings.spacingJ,
                                top = CompanionTheme.spacings.spacingD
                            ),
                            verticalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingB),
                            modifier = Modifier
                                .padding(
                                    start = CompanionTheme.spacings.spacingD,
                                    end = CompanionTheme.spacings.spacingD,
                                ),
                            state = listState,
                        ) {
                            items(
                                count = budget.size,
                                key = { index -> budget[index].uuid },
                            ) { index ->

                                val item = budget[index]

                                BudgetItem(item)
                            }
                        }
                    }
                }

                BudgetUiState.Error -> {
                    EmptyState(
                        stringResource(R.string.budget_empty_content_message),
                    )

                    onError(
                        stringResource(R.string.common_error_title),
                        stringResource(R.string.budget_error_message)
                    )
                }
            }
        }
    }
}

private fun getBudget(
    filterState: FilterState,
    uiModel: IBudgetUIModel
) {
    val (start, end) = filterState.getFilter()
    uiModel.getBudgetData(start, end)
}