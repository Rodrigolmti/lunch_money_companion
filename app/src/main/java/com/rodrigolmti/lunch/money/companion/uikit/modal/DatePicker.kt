@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.uikit.modal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadingGrey
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun LunchDatePicker(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: () -> Unit
) {
    DatePickerDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest()
                onDateSelected()
            }) {
                Text(
                    text = "Confirm",
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text(
                    text = "Cancel",
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                )
            }
        }
    ) {
        DatePicker(
            showModeToggle = false,
            title = {
                Text(
                    text = "Select Transaction Date",
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                    modifier = Modifier.padding(
                        PaddingValues(
                            start = 24.dp,
                            end = 12.dp,
                            top = 16.dp
                        )
                    )
                )
            },
            state = datePickerState,
            dateValidator = { millis ->
                millis <= System.currentTimeMillis()
            },
            colors = DatePickerDefaults.colors(
                dayContentColor = White,
                selectedDayContentColor = SunburstGold,
                todayContentColor = FadingGrey,
                weekdayContentColor = White,
            )
        )
    }
}