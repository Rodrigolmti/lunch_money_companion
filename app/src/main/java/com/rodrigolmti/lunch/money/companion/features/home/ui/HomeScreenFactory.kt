package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rodrigolmti.lunch.money.companion.features.home.model.HomeView
import com.rodrigolmti.lunch.money.companion.features.home.model.fakeAssetOverviewView
import com.rodrigolmti.lunch.money.companion.features.home.model.fakePeriodSummaryView
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal class DummyIHomeUIModel(state: HomeUiState = HomeUiState.Loading) : IHomeUIModel {
    override val viewState: StateFlow<HomeUiState> = MutableStateFlow(state)
    override fun getAccountOverview(start: Date, end: Date) {
        // no-op
    }

    override fun onRefresh(start: Date, end: Date) {
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
                    HomeView(
                        overviews = listOf(
                            fakeAssetOverviewView(),
                            fakeAssetOverviewView(),
                            fakeAssetOverviewView(),
                        ).toImmutableList(),
                        summary = fakePeriodSummaryView()
                    )
                )
            )
        )
}