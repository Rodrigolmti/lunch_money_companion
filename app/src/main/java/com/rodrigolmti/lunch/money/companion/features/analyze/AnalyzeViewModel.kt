package com.rodrigolmti.lunch.money.companion.features.analyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.onFailure
import com.rodrigolmti.lunch.money.companion.core.onSuccess
import com.rodrigolmti.lunch.money.companion.core.utils.getCurrentMonthDates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

internal abstract class IAnalyzeViewModel : ViewModel(), IAnalyzeUIModel

internal typealias GetGroupTransaction = suspend (Date, Date) -> Outcome<Map<String, Float>, LunchError>

internal class AnalyzeViewModel(
    private val getGroupTransaction: GetGroupTransaction
) : IAnalyzeViewModel() {

    private val _viewState =
        MutableStateFlow<AnalyzeUIState>(AnalyzeUIState.Loading)
    override val viewState: StateFlow<AnalyzeUIState> = _viewState

    init {
        val (start, end)  = getCurrentMonthDates()
        getGroup(start, end)
    }

    override fun getGroup(start: Date, end: Date) {
        viewModelScope.launch {
            _viewState.update { AnalyzeUIState.Loading }

            getGroupTransaction(start, end).onSuccess { result ->
                _viewState.update {
                    AnalyzeUIState.Success(result)
                }
            }.onFailure {
                _viewState.update {
                    AnalyzeUIState.Error
                }
            }
        }
    }
}