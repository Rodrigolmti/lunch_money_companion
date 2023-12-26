package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.runtime.Immutable
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView

@Immutable
internal sealed class HomeUiState {
    data object Loading : HomeUiState()

    data object Error : HomeUiState()

    data class Success(val overview: List<AssetOverviewView>) : HomeUiState()
}