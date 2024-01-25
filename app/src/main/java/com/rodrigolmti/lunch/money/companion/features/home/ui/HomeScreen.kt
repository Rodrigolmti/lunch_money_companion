@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.home.model.HomeView
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.FilterState
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionFilterBottomSheet
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun HomeScreen(
    uiModel: IHomeUIModel = DummyIHomeUIModel(),
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

    LaunchedEffect(Unit) {
        getSummary(filterState, uiModel)
    }

    Scaffold(
        topBar = {
            LunchAppBar(stringResource(R.string.home_title)) {
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
                    refreshAppData(filterState, uiModel)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh),
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
                        getSummary(filterState, uiModel)
                    }
                )
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            when (viewState) {
                is HomeUiState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                is HomeUiState.Error -> {
                    EmptyState(
                        stringResource(R.string.common_error_title),
                    )

                    onError(
                        stringResource(R.string.common_error_title),
                        stringResource(R.string.home_error_message)
                    )
                }

                is HomeUiState.Success -> {
                    val view = (viewState as HomeUiState.Success).view

                    BuildSuccessState(view)
                }
            }
        }
    }
}

private fun getSummary(
    filterState: FilterState,
    uiModel: IHomeUIModel
) {
    val (start, end) = filterState.getFilter()
    uiModel.getAccountOverview(start, end)
}

private fun refreshAppData(
    filterState: FilterState,
    uiModel: IHomeUIModel
) {
    val (start, end) = filterState.getFilter()
    uiModel.onRefresh(start, end)
}

@Composable
private fun BuildSuccessState(view: HomeView) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(top = CompanionTheme.spacings.spacingI)
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
                .padding(
                    top = CompanionTheme.spacings.spacingD,
                    start = CompanionTheme.spacings.spacingD,
                    end = CompanionTheme.spacings.spacingD,
                )
        ) {
            SummaryItem(view.summary)
        }

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
                .padding(CompanionTheme.spacings.spacingD)
        ) {
            OverviewItem(
                overviews = view.overviews
            )
        }

        VerticalSpacer(height = CompanionTheme.spacings.spacingJ)
    }
}

@Composable
@LunchMoneyPreview
private fun HomeScreenPreview(
    @PreviewParameter(HomeUIModelProvider::class) uiModel: IHomeUIModel
) {
    HomeScreen(uiModel)
}