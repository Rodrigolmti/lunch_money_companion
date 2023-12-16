package com.rodrigolmti.lunch.money.uikit.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> StateConsumer(
    viewState: T,
    content: @Composable (T) -> Unit,
    onEvent: @Composable (T) -> Unit
) {
    content(viewState)

    val eventHandled = remember { mutableStateOf(false) }

    LaunchedEffect(viewState) {
        eventHandled.value = false
    }

    if (!eventHandled.value) {
        onEvent(viewState)
        eventHandled.value = true
    }
}