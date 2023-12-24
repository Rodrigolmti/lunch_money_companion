package com.rodrigolmti.lunch.money.features.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.rodrigolmti.lunch.money.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.uikit.theme.Body
import com.rodrigolmti.lunch.money.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.uikit.theme.LunchMoneyTheme
import com.rodrigolmti.lunch.money.uikit.theme.SilverLining
import kotlinx.coroutines.flow.MutableStateFlow

private val DummyISettingsUIModel = object : ISettingsUIModel {
    override val viewState = MutableStateFlow<SettingsScreenUiState>(SettingsScreenUiState.Idle)

    override fun logout() {
        // no-op
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    uiModel: ISettingsUIModel = DummyISettingsUIModel,
    onLogoutRequested: () -> Unit = {}
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()
    val padding = PaddingValues(start = 16.dp, end = 16.dp)

    LaunchedEffect(Unit) {
        if (viewState is SettingsScreenUiState.Logout) {
            onLogoutRequested()
        }
    }

    Scaffold {
        Column {
            LunchAppBar("Settings")

            VerticalSpacer(height = 16.dp)

            Text(
                text = "Rodrigo Lopes Martins",
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = BodyBold,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = "rodrigolmti@gmail.com",
                modifier = Modifier
                    .padding(padding),
                color = SilverLining,
                style = Body,
            )

            VerticalSpacer(height = 32.dp)

            LunchButton(
                label = "Logout",
                modifier = Modifier
                    .padding(padding),
            ) {
                uiModel.logout()
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    LunchMoneyTheme {
        SettingsScreen()
    }
}