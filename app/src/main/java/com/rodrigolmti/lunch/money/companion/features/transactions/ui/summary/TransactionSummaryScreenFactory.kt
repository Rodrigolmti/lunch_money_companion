package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.core.utils.ValueGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date

internal class DummyITransactionSummaryUIModel(
    state: TransactionsSummaryUIState = TransactionsSummaryUIState.Loading
) : TransactionsSummaryUIModel {
    override val viewState = MutableStateFlow(state)
    override fun getSummary(start: Date, end: Date) {
        // no-op
    }
}

internal class TransactionSummaryUIModelProvider :
    PreviewParameterProvider<TransactionsSummaryUIModel> {
    override val values: Sequence<TransactionsSummaryUIModel>
        get() = sequenceOf(
            DummyITransactionSummaryUIModel(TransactionsSummaryUIState.Loading),
            DummyITransactionSummaryUIModel(TransactionsSummaryUIState.Error),
            DummyITransactionSummaryUIModel(
                TransactionsSummaryUIState.Success(fakeTransactionSummaryView())
            ),
            DummyITransactionSummaryUIModel(
                TransactionsSummaryUIState.Success(
                    TransactionSummaryView(
                        income = emptyMap(),
                        totalIncome = 0f,
                        expense = emptyMap(),
                        totalExpense = 0f,
                        net = 0f,
                        currency = ValueGenerator.currency()
                    )
                )
            ),
        )
}