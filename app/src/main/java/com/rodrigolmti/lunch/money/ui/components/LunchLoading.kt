package com.rodrigolmti.lunch.money.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.ui.theme.MidnightSlate
import com.rodrigolmti.lunch.money.ui.theme.White

@Composable
fun LunchLoading() {
    CircularProgressIndicator(
        strokeWidth = 3.dp,
        modifier = Modifier.size(25.dp),
        color = White,
        trackColor = MidnightSlate,
    )
}

@Preview
@Composable
fun LoadingPreview() {
    LunchLoading()
}