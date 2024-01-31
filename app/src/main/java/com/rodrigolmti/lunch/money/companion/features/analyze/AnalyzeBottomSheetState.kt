package com.rodrigolmti.lunch.money.companion.features.analyze

sealed class AnalyzeBottomSheetState {
    data object Idle : AnalyzeBottomSheetState()
    data object GetGroupError : AnalyzeBottomSheetState()
    data object FilterModal : AnalyzeBottomSheetState()
}