@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.uikit.theme.Header
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold

@Composable
internal fun LunchAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actionIcon: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        navigationIcon = {
            onBackClick?.let {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = SilverLining,
                    modifier = androidx.compose.ui.Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .clickable {
                            onBackClick()
                        }
                )
            }
        },
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
    LunchAppBar("Lunch Money") {}
}