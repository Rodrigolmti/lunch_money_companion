package com.rodrigolmti.lunch.money.ui.features.home

import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetOverviewModel

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
    data class Success(val overview: List<AssetOverviewModel>) : HomeUiState()
}