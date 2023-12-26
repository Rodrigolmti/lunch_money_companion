package com.rodrigolmti.lunch.money.companion.features.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.companion.uikit.theme.LunchMoneyCompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import kotlinx.coroutines.flow.MutableStateFlow

private val DummyISettingsUIModel = object : ISettingsUIModel {
    override val viewState = MutableStateFlow<SettingsScreenUiState>(SettingsScreenUiState.Idle)

    override fun logout() {
        // no-op
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun SettingsScreen(
    uiModel: ISettingsUIModel = DummyISettingsUIModel,
    onLogout: () -> Unit = {},
    onTermsOfUseClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()
    val padding = PaddingValues(start = 16.dp, end = 16.dp)

    LaunchedEffect(Unit) {
        if (viewState is SettingsScreenUiState.Logout) {
            onLogout()
        }
    }

    Scaffold(
        topBar = {
            LunchAppBar("Settings")
        }
    ) {
        Column {
            VerticalSpacer(height = 72.dp)

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp),
            ) {

                Icon(
                    Icons.Default.AccountCircle,
                    tint = GraphiteWhisper,
                    modifier = Modifier
                        .size(42.dp),
                    contentDescription = "Profile",
                )
                VerticalSpacer(8.dp)
                Column {
                    Text(
                        text = "Rodrigo Lopes Martins",
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = SilverLining,
                        style = BodyBold,
                    )
                    VerticalSpacer(8.dp)
                    Text(
                        text = "rodrigolmti@gmail.com",
                        modifier = Modifier
                            .padding(padding),
                        color = SilverLining,
                        style = Body,
                    )
                }
            }

            SettingsItem(label = "Terms of Use", icon = Icons.Default.Person) {
                onTermsOfUseClick()
            }
            SettingsItem(
                label = "Lunch Money for Android",
                description = "1.0.0 made with ❤️ by Rodrigo L.M",
                icon = Icons.Default.Person
            )
            SettingsItem(
                label = "Sign Out",
                color = FadedBlood,
                icon = Icons.Default.ExitToApp
            ) {
                uiModel.logout()
            }

            VerticalSpacer(32.dp)
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    LunchMoneyCompanionTheme {
        SettingsScreen()
    }
}