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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.BuildConfig
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.PRIVACY_POLICY_URL
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun SettingsScreen(
    uiModel: ISettingsUIModel = DummyISettingsUIModel(),
    onLogout: () -> Unit = {},
    onTermsOfUseClick: (String) -> Unit = {},
    onDonationClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()
    val padding = PaddingValues(
        start = CompanionTheme.spacings.spacingD,
        end = CompanionTheme.spacings.spacingD
    )

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
                    VerticalSpacer(height = CompanionTheme.spacings.spacingJ)

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(CompanionTheme.spacings.spacingD),
                    ) {

                        Icon(
                            Icons.Default.AccountCircle,
                            tint = GraphiteWhisper,
                            modifier = Modifier
                                .size(42.dp),
                            contentDescription = stringResource(R.string.settings_screen_profile_label),
                        )
                        VerticalSpacer(CompanionTheme.spacings.spacingB)
                        Column {
                            Text(
                                text = data.userName,
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = SilverLining,
                                style = CompanionTheme.typography.bodyBold,
                            )
                            VerticalSpacer(CompanionTheme.spacings.spacingB)
                            Text(
                                text = data.userEmail,
                                modifier = Modifier
                                    .padding(padding),
                                color = SilverLining,
                                style = CompanionTheme.typography.body,
                            )
                        }
                    }

                    SettingsItem(
                        label = stringResource(R.string.settings_screen_terms_label),
                        icon = painterResource(id = R.drawable.ic_terms)
                    ) {
                        onTermsOfUseClick(PRIVACY_POLICY_URL)
                    }
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_app_description),
                        description = stringResource(
                            R.string.settings_screen_app_version,
                            BuildConfig.VERSION_NAME
                        ),
                        icon = painterResource(id = R.drawable.ic_build)
                    )
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_donate_action),
                        icon = painterResource(id = R.drawable.ic_btc)
                    ) {
                        onDonationClick()
                    }
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_sign_out_action),
                        color = FadedBlood,
                        icon = painterResource(id = R.drawable.ic_sign_out)
                    ) {
                        uiModel.logout()
                    }

                    VerticalSpacer(CompanionTheme.spacings.spacingF)
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
    CompanionTheme {
        SettingsScreen(uiModel)
    }
}