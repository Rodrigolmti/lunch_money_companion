package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.features.home.model.fakeAssetOverviewView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class DummyIHomeUIModel(state: HomeUiState = HomeUiState.Loading) : IHomeUIModel {
    override val viewState: StateFlow<HomeUiState> = MutableStateFlow(state)

    override fun getAccountOverview() {
        // no-op
    }

    override fun onRefresh() {
        // no-op
    }
}

internal class HomeUIModelProvider : PreviewParameterProvider<IHomeUIModel> {
    override val values: Sequence<IHomeUIModel>
        get() = sequenceOf(
            DummyIHomeUIModel(HomeUiState.Loading),
            DummyIHomeUIModel(HomeUiState.Error),
            DummyIHomeUIModel(
                HomeUiState.Success(
                    listOf(
                        fakeAssetOverviewView(),
                        fakeAssetOverviewView(),
                        fakeAssetOverviewView(),
                    )
                )
            )
        )
}