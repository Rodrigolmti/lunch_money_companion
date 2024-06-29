@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold

@Composable
internal fun LunchAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actionIcon: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        navigationIcon = {
            onBackClick?.let {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = SilverLining,
                    modifier = Modifier
                        .padding(
                            start = CompanionTheme.spacings.spacingD,
                            end = CompanionTheme.spacings.spacingD
                        )
                        .clickable {
                            onBackClick()
                        }
                )
            }
        },
        actions = {
            actionIcon.invoke(this)
        },
        title = {
            Text(
                text = title,
                color = SunburstGold,
                style = CompanionTheme.typography.header,
            )
        })
}

@Composable
@LunchMoneyPreview
private fun LunchAppBarPreview() {
    CompanionTheme {
        Column {
            LunchAppBar("Lunch Money") {}

            VerticalSpacer(height = CompanionTheme.spacings.spacingD)

            LunchAppBar("Lunch Money", {}) {}
        }
    }
}