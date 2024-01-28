package com.rodrigolmti.lunch.money.companion.features.recurring

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList

internal class RecurringUIModelProvider : PreviewParameterProvider<IRecurringUIModel> {
    override val values: Sequence<IRecurringUIModel>
        get() = sequenceOf(
            DummyIRecurringUIModel(RecurringUiState.Loading),
            DummyIRecurringUIModel(RecurringUiState.Error),
            DummyIRecurringUIModel(
                RecurringUiState.Success(
                    listOf(
                        fakeRecurringView(),
                        fakeRecurringView(),
                        fakeRecurringView(),
                    ).toImmutableList()
                )
            ),
        )
}