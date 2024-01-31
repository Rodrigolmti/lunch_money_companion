package com.rodrigolmti.lunch.money.companion.features.analyze

sealed class AnalyzeUIState {
    data object Loading : AnalyzeUIState()

    data object Error : AnalyzeUIState()

    data class Success(val group: Map<String, Float>) : AnalyzeUIState()
}