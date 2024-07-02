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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.BuildConfig
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.PRIVACY_POLICY_URL
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchDropDown
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.modal.ConfirmationDialog
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import java.util.Currency

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun SettingsScreen(
    uiModel: ISettingsUIModel = DummyISettingsUIModel(),
    onLogout: () -> Unit = {},
    onTermsOfUseClick: () -> Unit = {},
    onDonationClick: () -> Unit = {},
    onAnalyzeClick: () -> Unit = {},
    onWhatsNewClick: () -> Unit = {},
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    val options = listOf(
        Currency.getInstance("USD"),
        Currency.getInstance("EUR"),
        Currency.getInstance("GBP"),
        Currency.getInstance("JPY"),
        Currency.getInstance("CNY"),
        Currency.getInstance("BRL"),
        Currency.getInstance("RUB"),
        Currency.getInstance("INR"),
        Currency.getInstance("AUD"),
        Currency.getInstance("CAD"),
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

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

    if (openAlertDialog.value) {
        ConfirmationDialog(
            onConfirmation = { uiModel.logout() },
            onFinish = { openAlertDialog.value = false },
            dialogTitle = stringResource(id = R.string.settings_logout_title),
            dialogText = stringResource(R.string.settings_logout_description)
        )
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

                LaunchedEffect(data.currency) {
                    selectedOption = options.find { it.currencyCode == data.currency } ?: options[0]
                }

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
//                    SettingsItem(
//                        label = stringResource(R.string.settings_screen_analyze_action),
//                        icon = painterResource(id = R.drawable.ic_analyze)
//                    ) {
//                        onAnalyzeClick()
//                    }

                    Text(
                        text = stringResource(R.string.settings_currency_description),
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(
                                start = CompanionTheme.spacings.spacingD,
                                end = CompanionTheme.spacings.spacingD,
                            ),
                        color = SilverLining,
                        overflow = TextOverflow.Ellipsis,
                        style = CompanionTheme.typography.bodySmall,
                    )

                    VerticalSpacer(CompanionTheme.spacings.spacingA)

                    LunchDropDown(
                        modifier = Modifier
                            .padding(
                                start = CompanionTheme.spacings.spacingD,
                                end = CompanionTheme.spacings.spacingD,
                                top = CompanionTheme.spacings.spacingC,
                                bottom = CompanionTheme.spacings.spacingC,
                            )
                            .fillMaxWidth(),
                        selectedOption = selectedOption,
                        onOptionSelected = {
                            selectedOption = it
                            uiModel.updateCurrencyData(it.currencyCode)
                        },
                        options = options,
                        label = stringResource(R.string.settings_currency_label),
                        expanded = expanded,
                        getSelectedLabel = {
                            "${it.displayName} (${it.currencyCode})"
                        },
                        onExpandedChange = {
                            expanded = it
                        }
                    )

                    VerticalSpacer(CompanionTheme.spacings.spacingB)

                    SettingsItem(
                        label = stringResource(R.string.settings_screen_terms_label),
                        icon = painterResource(id = R.drawable.ic_terms),
                        color = GraphiteWhisper,
                    ) {
                        onTermsOfUseClick()
                    }
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_app_description),
                        description = stringResource(
                            R.string.settings_screen_app_version,
                            BuildConfig.VERSION_NAME
                        ),
                        icon = painterResource(id = R.drawable.ic_build),
                        color = GraphiteWhisper,
                    )
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_donate_action),
                        icon = painterResource(id = R.drawable.ic_btc),
                        color = GraphiteWhisper,
                    ) {
                        onDonationClick()
                    }
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_whats_new_action),
                        color = GraphiteWhisper,
                        icon = painterResource(id = R.drawable.ic_new)
                    ) {
                        onWhatsNewClick()
                    }
                    SettingsItem(
                        label = stringResource(R.string.settings_screen_sign_out_action),
                        color = FadedBlood,
                        icon = painterResource(id = R.drawable.ic_sign_out)
                    ) {
                        openAlertDialog.value = true
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