package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun LunchLoading() {
    CircularProgressIndicator(
        strokeWidth = 3.dp,
        modifier = Modifier.size(25.dp),
        color = White,
        trackColor = MidnightSlate,
    )
}

@Composable
@LunchMoneyPreview
private fun LoadingPreview() {
    LunchLoading()
}