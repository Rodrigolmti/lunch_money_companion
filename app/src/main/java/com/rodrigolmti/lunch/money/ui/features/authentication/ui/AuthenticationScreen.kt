@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.rodrigolmti.lunch.money.ui.features.authentication.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.ui.components.BouncingImageAnimation
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.ui.components.LunchButton
import com.rodrigolmti.lunch.money.ui.components.LunchTextField
import com.rodrigolmti.lunch.money.ui.components.TiledBackgroundScreen
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.theme.BackgroundDefault
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.ContentBrand
import com.rodrigolmti.lunch.money.ui.theme.ContentDefault
import com.rodrigolmti.lunch.money.ui.theme.Header
import com.rodrigolmti.lunch.money.ui.theme.LunchMoneyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private object DummyAuthenticationUIModel : IAuthenticationUIModel {
    override val viewState = MutableStateFlow<AuthenticationUiState>(AuthenticationUiState.Idle)
    override fun onGetStartedClicked(token: String) {
        // no-op
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    uiModel: IAuthenticationUIModel = DummyAuthenticationUIModel
) {
    Scaffold(
        modifier = Modifier
    ) {
        val viewState by uiModel.viewState.collectAsState()
        var apiToken by rememberSaveable { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current
        val bottomSheetState = rememberModalBottomSheetState()
        val coroutineScope = rememberCoroutineScope()

        if (viewState.isError()) {
            ErrorBottomSheet(bottomSheetState, coroutineScope)
        }

        TiledBackgroundScreen()

        Center {
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = ContentDefault
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
                        text = "Welcome Back!",
                        textAlign = TextAlign.Center,
                        style = Header,
                        color = Color.White,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )

                    VerticalSpacer(height = 16.dp)

                    LunchTextField(
                        visualTransformation = PasswordVisualTransformation(),
                        label = "Api Token",
                        text = apiToken
                    ) {
                        apiToken = it
                    }

                    VerticalSpacer(height = 16.dp)

                    LunchButton(label = "LOGIN", isLoading = viewState.isLoading()) {
                        keyboardController?.hide()
                        uiModel.onGetStartedClicked(apiToken)
                    }

                    VerticalSpacer(height = 16.dp)

                    Row {
                        Text(
                            text = "Don't have an API Key?",
                            textAlign = TextAlign.Center,
                            style = Body,
                            color = Color.White
                        )
                        HorizontalSpacer(4.dp)
                        Text(
                            text = "Get one here.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable { },
                            style = Body.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = ContentBrand
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun ErrorBottomSheet(
    bottomSheetState: SheetState,
    coroutineScope: CoroutineScope
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet = false
        },
        containerColor = BackgroundDefault,
        sheetState = bottomSheetState,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                "Connection Issue Detected",
                style = Header,
                color = Color.White,
            )
            VerticalSpacer(height = 16.dp)
            Text(
                "We're having trouble processing your request. This could be due to a server error, an invalid API key, or a problem with your network connection. Please verify your API key and ensure your device has a stable internet connection. If the issue persists, try again later.",
                style = Body,
                color = Color.White,
            )
            VerticalSpacer(height = 24.dp)
            LunchButton(
                label = "OK",
            ) {
                coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }
            VerticalSpacer(height = 32.dp)
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AuthenticationScreenPreview() {
    LunchMoneyTheme {
        AuthenticationScreen()
    }
}
