package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.core.utils.getCurrentMonthDates
import com.rodrigolmti.lunch.money.companion.features.home.model.HomeView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

internal abstract class IHomeViewModel : ViewModel(), IHomeUIModel

private typealias GetAccountOverview = suspend (start: Date, end: Date) -> Outcome<HomeView, LunchError>
private typealias RefreshUserData = suspend () -> Outcome<Unit, LunchError>

internal class HomeViewModel(
    private val getUserAccountOverview: GetAccountOverview,
    private val refreshUserData: RefreshUserData,
) : IHomeViewModel() {

    private val _viewState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    override val viewState: StateFlow<HomeUiState> = _viewState

    init {
        val (start, end) = getCurrentMonthDates()
        getAccountOverview(start, end)
    }

    override fun getAccountOverview(start: Date, end: Date) {
        viewModelScope.launch {
            _viewState.update { HomeUiState.Loading }
            getUserAccountOverview(start, end).onSuccess { result ->
                _viewState.update {
                    HomeUiState.Success(result)
                }
            }.onFailure {
                _viewState.update {
                    HomeUiState.Error
                }
            }
        }
    }

    override fun onRefresh(start: Date, end: Date) {
        viewModelScope.launch {
            _viewState.update { HomeUiState.Loading }
            refreshUserData().onSuccess {
                getAccountOverview(start, end)
            }.onFailure {
                _viewState.update {
                    HomeUiState.Error
                }
            }
        }
    }
}