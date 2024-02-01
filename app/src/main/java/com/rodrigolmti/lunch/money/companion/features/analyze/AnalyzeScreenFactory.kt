package com.rodrigolmti.lunch.money.companion.features.analyze

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date

internal class DummyIAnalyzeUIModel(
    state: AnalyzeUIState = AnalyzeUIState.Loading
) : IAnalyzeUIModel {
    override val viewState = MutableStateFlow(state)
    override fun getGroup(start: Date, end: Date) {
        // no-op
    }
}

internal class AnalyzeUIModelProvider :
    PreviewParameterProvider<IAnalyzeUIModel> {
    override val values: Sequence<IAnalyzeUIModel>
        get() = sequenceOf(
            DummyIAnalyzeUIModel(AnalyzeUIState.Loading),
            DummyIAnalyzeUIModel(AnalyzeUIState.Error),
            DummyIAnalyzeUIModel(
                AnalyzeUIState.Success(
                    mapOf(
                        "Groceries" to 100f,
                        "Eating Out" to 200f,
                        "Transport" to 300f,
                        "Entertainment" to 400f,
                        "Bills" to 500f,
                        "uncategorized" to 600f)
                )
            ),
        )
}