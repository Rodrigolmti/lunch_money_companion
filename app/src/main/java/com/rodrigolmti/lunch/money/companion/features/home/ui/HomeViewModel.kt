package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class IHomeViewModel : ViewModel(), IHomeUIModel

private typealias GetAccountOverview = suspend () -> Outcome<List<AssetOverviewView>, LunchError>
private typealias RefreshUserData = suspend () -> Outcome<Unit, LunchError>

class HomeViewModel(
    private val getUserAccountOverview: GetAccountOverview,
    private val refreshUserData: RefreshUserData,
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

    override fun onRefresh() {
        viewModelScope.launch {
            _viewState.value = HomeUiState.Loading
            refreshUserData().onSuccess {
                getAccountOverview()
            }.onFailure {
                _viewState.value = HomeUiState.Error
            }
        }
    }
}