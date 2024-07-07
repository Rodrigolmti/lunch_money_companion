package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal class DummyIBudgetUIModel(state: BudgetUiState = BudgetUiState.Loading) : IBudgetUIModel {
    override val viewState: StateFlow<BudgetUiState> = MutableStateFlow(state)

    override fun getBudgetList(start: Date, end: Date) {
        // no-op
    }
}

internal class BudgetUIModelProvider : PreviewParameterProvider<IBudgetUIModel> {
    override val values: Sequence<IBudgetUIModel>
        get() = sequenceOf(
            DummyIBudgetUIModel(BudgetUiState.Loading),
            DummyIBudgetUIModel(BudgetUiState.Error),
            DummyIBudgetUIModel(
                BudgetUiState.Success(
                    persistentListOf(
                        fakeBudgetView(),
                        fakeBudgetView(),
                        fakeBudgetView(),
                    )
                )
            ),
        )
}