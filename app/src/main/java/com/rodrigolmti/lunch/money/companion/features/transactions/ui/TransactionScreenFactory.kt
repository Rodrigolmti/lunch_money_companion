package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.features.transactions.model.fakeTransactionView
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.Date

internal class DummyITransactionsUIModel(state: TransactionsUiState = TransactionsUiState.Loading) :
    ITransactionsUIModel {
    override val viewState = MutableStateFlow(state)
    override fun getTransactions(start: Date, end: Date) {
        // no-op
    }
}

internal class TransactionsUIModelProvider : PreviewParameterProvider<ITransactionsUIModel> {
    override val values: Sequence<ITransactionsUIModel>
        get() = sequenceOf(
            DummyITransactionsUIModel(TransactionsUiState.Loading),
            DummyITransactionsUIModel(TransactionsUiState.Error),
            DummyITransactionsUIModel(
                TransactionsUiState.Success(
                    persistentListOf(
                        fakeTransactionView(),
                        fakeTransactionView(),
                        fakeTransactionView(),
                    )
                )
            ),
        )
}