package com.rodrigolmti.lunch.money.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.onFailure
import com.rodrigolmti.lunch.money.core.onSuccess
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetOverviewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class IHomeViewModel : ViewModel(), IHomeUIModel

class HomeViewModel(
    private val getUserAccountOverview: suspend () -> Outcome<List<AssetOverviewModel>, LunchError>
) : IHomeViewModel() {

    private val _viewState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    override val viewState: StateFlow<HomeUiState> = _viewState

    init {
        getAccountOverview()
    }

    override fun getAccountOverview() {
        viewModelScope.launch {
            _viewState.value = HomeUiState.Loading
            getUserAccountOverview().onSuccess {
                _viewState.value = HomeUiState.Success(it)
            }.onFailure {
                _viewState.value = HomeUiState.Error
            }
        }
    }
}