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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.BuildConfig
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.companion.uikit.theme.LunchMoneyCompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun SettingsScreen(
    uiModel: ISettingsUIModel = DummyISettingsUIModel(),
    onLogout: () -> Unit = {},
    onTermsOfUseClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()
    val padding = PaddingValues(start = 16.dp, end = 16.dp)

    if (viewState is SettingsScreenUiState.Logout) {
        LaunchedEffect(Unit) {
            onLogout()
        }
    }

    LaunchedEffect(Unit) {
        uiModel.getUserData()
    }

    Scaffold(
        topBar = {
            LunchAppBar(stringResource(R.string.settings_screen_title))
        }
    ) {

        when (viewState) {
            is SettingsScreenUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            is SettingsScreenUiState.Success -> {
                val data = (viewState as SettingsScreenUiState.Success).data

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
                            contentDescription = stringResource(R.string.settings_screen_profile_label),
                        )
                        VerticalSpacer(8.dp)
                        Column {
                            Text(
                                text = data.userName,
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = SilverLining,
                                style = BodyBold,
                            )
                            VerticalSpacer(8.dp)
                            Text(
                                text = data.userEmail,
                                modifier = Modifier
                                    .padding(padding),
                                color = SilverLining,
                                style = Body,
                            )
                        }
                    }

                    SettingsItem(
                        label = stringResource(R.string.settings_screen_terms_label),
                        icon = Icons.Default.Person
                    ) {
                        onTermsOfUseClick()
                    }
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_app_description),
                        description = stringResource(
                            R.string.settings_screen_app_version,
                            BuildConfig.VERSION_NAME
                        ),
                        icon = Icons.Default.Person
                    )
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_sign_out_action),
                        color = FadedBlood,
                        icon = Icons.Default.ExitToApp
                    ) {
                        uiModel.logout()
                    }

                    VerticalSpacer(32.dp)
                }
            }

            else -> {
                // no-op
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun SettingsScreenPreview(
    @PreviewParameter(SettingsUIModelProvider::class) uiModel: ISettingsUIModel
) {
    LunchMoneyCompanionTheme {
        SettingsScreen(uiModel)
    }
}