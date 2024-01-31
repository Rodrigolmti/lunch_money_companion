package com.rodrigolmti.lunch.money.companion.features.analyze

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal interface IAnalyzeUIModel {
    val viewState: StateFlow<AnalyzeUIState>

    fun getGroup(start: Date, end: Date)
}