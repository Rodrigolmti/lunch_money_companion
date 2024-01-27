package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.features.transactions.model.fakeTransactionView
import kotlinx.coroutines.flow.MutableStateFlow

internal class DummyITransactionDetailUIModel(
    state: TransactionDetailUiState = TransactionDetailUiState.Loading
) : ITransactionDetailUIModel {
    override val viewState = MutableStateFlow(state)
    override fun getTransaction(id: Int) {
        // no-op
    }
}

internal class TransactionDetailUIModelProvider :
    PreviewParameterProvider<ITransactionDetailUIModel> {
    override val values: Sequence<ITransactionDetailUIModel>
        get() = sequenceOf(
            DummyITransactionDetailUIModel(TransactionDetailUiState.Loading),
            DummyITransactionDetailUIModel(TransactionDetailUiState.Error),
            DummyITransactionDetailUIModel(
                TransactionDetailUiState.Success(
                    fakeTransactionView()
                )
            ),
        )
}