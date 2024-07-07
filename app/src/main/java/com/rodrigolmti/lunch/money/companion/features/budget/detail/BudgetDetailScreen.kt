package com.rodrigolmti.lunch.money.companion.features.budget.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.FilterState
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.components.MonthSelector
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
internal fun BudgetDetailScreen(
    uiModel: IBudgetDetailUIModel = DummyIBudgetDetailUIModel(),
    budgetId: Int,
    onBackClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()
    var filterState by remember { mutableStateOf(FilterState()) }
    var value by remember { mutableLongStateOf(0) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        uiModel.getBudget(budgetId)
    }

    Scaffold(
        topBar = {
            LunchAppBar(
                title = "Budget Detail",
                onBackClick = onBackClick,
            )
        },
        modifier = Modifier,
    ) { padding ->
        when (viewState) {
            is BudgetDetailUiState.Error -> {
                EmptyState(
                    stringResource(R.string.budget_empty_content_message),
                )
            }
            is BudgetDetailUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            is BudgetDetailUiState.Success -> {
                val budget = (viewState as BudgetDetailUiState.Success).budget

                BuildBody(
                    budget = budget,
                    scrollState = scrollState,
                    filterState = filterState,
                    value = value,
                    onPreviousMonthClick = {
                        filterState = filterState.decrease()
                    },
                    onNextMonthClick = {
                        filterState = filterState.increase()
                    },
                )
            }
        }
    }
}

@Composable
private fun BuildBody(
    budget: BudgetView,
    scrollState: ScrollState,
    filterState: FilterState,
    value: Long,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
) {
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

        MonthSelector(
            label = filterState.getDisplay(),
            onPreviousMonthClick = {
                onPreviousMonthClick()
            },
            onNextMonthClick = {
                onNextMonthClick()
            },
        )
        
        with(budget.items[filterState.getStartDateAsString()]) {
            BudgetDataItem(item = this)
        }

        LunchTextField(
            label = "Budget Value",
            text = value.toString(),
            enabled = false,
            disabledTextColor = White,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {

                }
        )

        Spacer(modifier = Modifier.weight(1f))

        LunchButton(
            label = "Update",
            isLoading = false,
            isEnabled = false,
        ) {

        }
    }
}

@Composable
@LunchMoneyPreview
private fun BudgetDetailScreenPreview(
    @PreviewParameter(BudgetDetailUIModelProvider::class) uiModel: IBudgetDetailUIModel
) {
    CompanionTheme {
        BudgetDetailScreen(uiModel, 1)
    }
}