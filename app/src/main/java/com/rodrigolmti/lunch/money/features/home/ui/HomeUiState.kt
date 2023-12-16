package com.rodrigolmti.lunch.money.features.home.ui

import com.rodrigolmti.lunch.money.features.home.model.AssetOverviewView

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
    data class Success(val overview: List<AssetOverviewView>) : HomeUiState()
}