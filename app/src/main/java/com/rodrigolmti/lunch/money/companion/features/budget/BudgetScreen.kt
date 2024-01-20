@file:OptIn(ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.budget

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.core.utils.formatToMonthYear
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.FilterState
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionFilterBottomSheet
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
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
                        Icons.Filled.List,
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
                IconButton(onClick = {
                    getBudget(filterState, uiModel)
                }) {
                    Icon(
                        Icons.Filled.Refresh,
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
                                count = budget.size,
                                key = { index -> budget[index].uuid },
                            ) { index ->

                                val item = budget[index]

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
                                            .padding(16.dp),
                                    ) {

                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {

                                            Text(
                                                item.category,
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 2,
                                                modifier = Modifier.weight(1f),
                                                color = White,
                                                style = BodyBold,
                                            )

                                            IconButton(
                                                onClick = {}
                                            ) {
                                                Icon(
                                                    Icons.Filled.KeyboardArrowDown,
                                                    contentDescription = null,
                                                    tint = SilverLining,
                                                )
                                            }

                                        }

                                        item.items.forEach {

                                            if (it.totalTransactions > 0) {

                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {

                                                    Text(
                                                        "Transactions:",
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 2,
                                                        modifier = Modifier.weight(1f),
                                                        color = White,
                                                        style = Body,
                                                    )

                                                    Text(
                                                        text = it.totalTransactions.toString(),
                                                        color = White,
                                                        style = BodyBold,
                                                    )

                                                }

                                                VerticalSpacer(height = 16.dp)

                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {

                                                    Text(
                                                        "Budget value:",
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 2,
                                                        modifier = Modifier.weight(1f),
                                                        color = White,
                                                        style = Body,
                                                    )

                                                    Text(
                                                        text = formatCurrency(
                                                            it.totalBudget,
                                                            it.currency
                                                        ),
                                                        color = SunburstGold,
                                                        style = BodyBold,
                                                    )

                                                }

                                                VerticalSpacer(height = 16.dp)

                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {

                                                    Text(
                                                        "Total Spending:",
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 2,
                                                        modifier = Modifier.weight(1f),
                                                        color = White,
                                                        style = Body,
                                                    )

                                                    Text(
                                                        text = formatCurrency(
                                                            it.totalSpending,
                                                            it.currency
                                                        ),
                                                        color = if (it.totalSpending > it.totalBudget) FadedBlood else EmeraldSpring,
                                                        style = BodyBold,
                                                    )

                                                }

                                            } else {

                                                Center {
                                                    Text(
                                                        "No budget found for this category",
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 2,
                                                        modifier = Modifier.weight(1f),
                                                        color = White,
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