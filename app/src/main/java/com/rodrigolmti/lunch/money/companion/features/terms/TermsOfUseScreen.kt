package com.rodrigolmti.lunch.money.companion.features.terms

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun TermsOfUseScreen(
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
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.terms_of_use_content),
                style = Body,
                color = White,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 72.dp, bottom = 32.dp)
            )
        }
    }
}