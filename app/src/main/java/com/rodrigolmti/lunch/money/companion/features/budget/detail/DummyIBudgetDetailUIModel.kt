package com.rodrigolmti.lunch.money.companion.features.budget.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.features.budget.fakeBudgetView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class DummyIBudgetDetailUIModel(state: BudgetDetailUiState = BudgetDetailUiState.Loading) :
    IBudgetDetailUIModel {
    override val viewState: StateFlow<BudgetDetailUiState> = MutableStateFlow(state)

    override fun getBudget(budgetId: Int) {
        // no-op
    }

    override fun updateBudget(budgetId: Int, value: Long) {
        TODO("Not yet implemented")
    }
}

internal class BudgetDetailUIModelProvider : PreviewParameterProvider<IBudgetDetailUIModel> {
    override val values: Sequence<IBudgetDetailUIModel>
        get() = sequenceOf(
            DummyIBudgetDetailUIModel(BudgetDetailUiState.Loading),
            DummyIBudgetDetailUIModel(BudgetDetailUiState.Error),
            DummyIBudgetDetailUIModel(
                BudgetDetailUiState.Success(
                    fakeBudgetView(),
                )
            ),
        )
}