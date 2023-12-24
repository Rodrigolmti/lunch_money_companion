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
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadingGrey
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.NightSkyMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun LunchTextField(
    label: String,
    text: String,
    readOnly: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
) {
    Column {
        Text(
            text = label.uppercase(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = Body,
        )

        VerticalSpacer(height = 8.dp)

        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            singleLine = true,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                cursorColor = White,
                focusedTextColor = if (readOnly) FadingGrey else White,
                focusedContainerColor = if (readOnly) NightSkyMist else MidnightSlate,
                unfocusedContainerColor = if (readOnly) NightSkyMist else NightSkyMist,
                disabledContainerColor = NightSkyMist,
                disabledTextColor = FadingGrey,
                unfocusedTextColor = if (readOnly) FadingGrey else White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }
}