@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)

package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.DEVELOPER_URL
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.openUrl
import com.rodrigolmti.lunch.money.companion.uikit.components.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.components.BouncingImageAnimation
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.components.TiledBackgroundScreen
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.Header
import com.rodrigolmti.lunch.money.companion.uikit.theme.LunchMoneyCompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun AuthenticationScreen(
    uiModel: IAuthenticationUIModel = DummyAuthenticationUIModel(),
    onUserAuthenticated: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
    ) {
        val viewState by uiModel.viewState.collectAsStateWithLifecycle()
        var apiToken by rememberSaveable { mutableStateOf("") }

        val keyboardState = LocalSoftwareKeyboardController.current

        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded })
        val scope = rememberCoroutineScope()

        val errorState = remember {
            mutableStateOf<AuthenticationScreenErrorState>(AuthenticationScreenErrorState.Idle)
        }

        val context = LocalContext.current

        when (viewState) {
            AuthenticationUiState.Error -> {
                LaunchedEffect(Unit) {
                    errorState.value = AuthenticationScreenErrorState.InvalidTokenError
                    scope.launch { sheetState.show() }
                }
            }

            AuthenticationUiState.Idle -> {
                // Do nothing
            }

            AuthenticationUiState.Loading -> {
                // Do nothing
            }

            AuthenticationUiState.NoConnectionError -> {
                LaunchedEffect(Unit) {
                    errorState.value = AuthenticationScreenErrorState.NoConnectionError
                    scope.launch { sheetState.show() }
                }
            }

            AuthenticationUiState.Success -> {
                onUserAuthenticated()
            }
        }

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                when (errorState.value) {
                    AuthenticationScreenErrorState.Idle -> {
                        // Do nothing
                    }

                    AuthenticationScreenErrorState.InvalidTokenError -> {
                        BottomSheetComponent(
                            stringResource(R.string.authentication_invalid_token_title),
                            stringResource(R.string.authentication_invalid_token_description),
                        ) {
                            scope.launch { sheetState.hide() }
                        }
                    }

                    AuthenticationScreenErrorState.NoConnectionError -> {
                        BottomSheetComponent(
                            stringResource(R.string.connection_error_title),
                            stringResource(R.string.connection_error_message),
                        ) {
                            scope.launch { sheetState.hide() }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            AuthenticationBody(
                apiToken = apiToken,
                context = context,
                isLoading = viewState.isLoading(),
                onTextChanged = {
                    apiToken = it
                },
                onTokenValidationError = {
                    errorState.value = AuthenticationScreenErrorState.InvalidTokenError
                    scope.launch { sheetState.show() }
                },
                onLoginClicked = {
                    keyboardState?.hide()
                    uiModel.onGetStartedClicked(apiToken)
                }
            )
        }
    }
}

@Composable
private fun AuthenticationBody(
    apiToken: String,
    context: Context,
    isLoading: Boolean = false,
    onTextChanged: (String) -> Unit,
    onTokenValidationError: () -> Unit = {},
    onLoginClicked: () -> Unit,
) {
    TiledBackgroundScreen()

    Center {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = CharcoalMist
            ),
            border = BorderStroke(
                width = Dp.Hairline,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                VerticalSpacer(height = 16.dp)

                BouncingImageAnimation()

                VerticalSpacer(height = 16.dp)

                Text(
                    text = stringResource(R.string.authentication_title),
                    textAlign = TextAlign.Center,
                    style = Header,
                    color = White,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )

                VerticalSpacer(height = 16.dp)

                LunchTextField(
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (apiToken.isBlank() || apiToken.length < 32) {
                                onTokenValidationError()
                                return@KeyboardActions
                            }

                            onLoginClicked()
                        }
                    ),
                    label = stringResource(R.string.authentication_token_label),
                    text = apiToken
                ) {
                    onTextChanged(it)
                }

                VerticalSpacer(height = 16.dp)

                LunchButton(
                    label = stringResource(R.string.authentication_action_label),
                    isLoading = isLoading
                ) {
                    if (apiToken.isBlank() || apiToken.length < 32) {
                        onTokenValidationError()
                        return@LunchButton
                    }

                    onLoginClicked()
                }

                VerticalSpacer(height = 16.dp)

                Row {
                    Text(
                        text = stringResource(R.string.authentication_get_key_one),
                        textAlign = TextAlign.Center,
                        style = Body,
                        color = White
                    )
                    HorizontalSpacer(4.dp)
                    Text(
                        text = stringResource(R.string.authentication_get_key_two),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            context.openUrl(DEVELOPER_URL)
                        },
                        style = Body.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = SunburstGold
                    )
                }
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun AuthenticationScreenPreview(
    @PreviewParameter(AuthenticationUIModelProvider::class) uiModel: IAuthenticationUIModel
) {
    LunchMoneyCompanionTheme {
        AuthenticationScreen(uiModel)
    }
}