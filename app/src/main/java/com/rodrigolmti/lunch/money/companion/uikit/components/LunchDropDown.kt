@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadingGrey
import com.rodrigolmti.lunch.money.companion.uikit.theme.NightSkyMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import java.util.Currency

@Composable
fun LunchDropDown(
    label: String,
    expanded: Boolean,
    options: List<Currency>,
    selectedOption: Currency,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (Currency) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier
            .padding(
                start = CompanionTheme.spacings.spacingD,
                end = CompanionTheme.spacings.spacingD,
                top = CompanionTheme.spacings.spacingC,
                bottom = CompanionTheme.spacings.spacingC,
            )
            .fillMaxWidth(),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = "${selectedOption.displayName} (${selectedOption.currencyCode})",
            onValueChange = {},
            label = {
                Text(
                    label,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                cursorColor = White,
                focusedTextColor = White,
                focusedContainerColor = NightSkyMist,
                unfocusedContainerColor = NightSkyMist,
                disabledContainerColor = NightSkyMist,
                disabledTextColor = FadingGrey,
                unfocusedTextColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(false)
            },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            "${option.displayName} (${option.currencyCode})",
                            color = SilverLining,
                            overflow = TextOverflow.Ellipsis,
                            style = CompanionTheme.typography.bodySmall,
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        onExpandedChange(false)
                    },
                )
            }
        }
    }
}