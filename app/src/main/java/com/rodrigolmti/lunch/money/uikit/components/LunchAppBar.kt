@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.uikit.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rodrigolmti.lunch.money.uikit.theme.Header
import com.rodrigolmti.lunch.money.uikit.theme.SunburstGold

@Composable
internal fun LunchAppBar(
    title: String,
    onBackClick: () -> Unit = {},
    actionIcon: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        actions = {
            actionIcon?.invoke()
        },
        title = {
            Text(
                text = title,
                color = SunburstGold,
                style = Header,
            )
        })
}

@Preview
@Composable
internal fun LunchAppBarPreview() {
    LunchAppBar("Lunch Money", {}, {})
}