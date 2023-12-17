package com.rodrigolmti.lunch.money.features.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.uikit.theme.Body
import com.rodrigolmti.lunch.money.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.uikit.theme.LunchMoneyTheme
import com.rodrigolmti.lunch.money.uikit.theme.SilverLining

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    onLogoutRequested: () -> Unit = {},
) {

    val padding = PaddingValues(start = 16.dp, end = 16.dp)

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
                onLogoutRequested()
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