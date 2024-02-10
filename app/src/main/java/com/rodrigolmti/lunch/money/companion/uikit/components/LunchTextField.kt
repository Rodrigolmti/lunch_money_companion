@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadingGrey
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.NightSkyMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun LunchTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    disabledTextColor: Color = FadingGrey,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label.uppercase(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )

        VerticalSpacer(height = CompanionTheme.spacings.spacingB)

        TextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(CompanionTheme.spacings.spacingNone),
            singleLine = true,
            readOnly = readOnly,
            enabled = enabled,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                cursorColor = White,
                focusedTextColor = if (readOnly) FadingGrey else White,
                focusedContainerColor = NightSkyMist,
                unfocusedContainerColor = NightSkyMist,
                disabledContainerColor = NightSkyMist,
                disabledTextColor = disabledTextColor,
                unfocusedTextColor = if (readOnly) FadingGrey else White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }
}