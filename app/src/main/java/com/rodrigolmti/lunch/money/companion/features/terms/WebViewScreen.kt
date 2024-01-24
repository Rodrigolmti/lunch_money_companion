package com.rodrigolmti.lunch.money.companion.features.terms

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun WebViewScreen(
    url: String,
    onBack: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            LunchAppBar(
                title = stringResource(R.string.terms_of_use_title),
                onBackClick = onBack,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = CompanionTheme.spacings.spacingH)
                .verticalScroll(scrollState)
        ) {
            AndroidView(factory = {
                WebView(it).apply {
                    settings.allowFileAccess = false
                    settings.allowContentAccess = false
                    settings.javaScriptEnabled = false
                    settings.domStorageEnabled = false
                    settings.databaseEnabled = false
                    settings.setGeolocationEnabled(false)
                    settings.setSupportZoom(false)
                    settings.setSupportMultipleWindows(false)
                    settings.setSupportZoom(false)
                    settings.builtInZoomControls = false
                    settings.displayZoomControls = false
                    settings.useWideViewPort = false

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            }, update = {
                it.loadUrl(url)
            })
        }
    }
}

