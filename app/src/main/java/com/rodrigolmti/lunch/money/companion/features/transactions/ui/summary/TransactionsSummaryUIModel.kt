package com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary

import kotlinx.coroutines.flow.StateFlow
import java.util.Date

internal interface TransactionsSummaryUIModel {
    val viewState: StateFlow<TransactionsSummaryUIState>
    fun getSummary(start: Date, end: Date)
}