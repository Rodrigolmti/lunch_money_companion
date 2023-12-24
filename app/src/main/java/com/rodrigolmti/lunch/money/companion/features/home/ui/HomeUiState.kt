package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView

@Immutable
sealed class HomeUiState {
    @Immutable
    data object Loading : HomeUiState()

    @Immutable
    data object Error : HomeUiState()

    @Immutable
    data class Success(val overview: List<AssetOverviewView>) : HomeUiState()
}