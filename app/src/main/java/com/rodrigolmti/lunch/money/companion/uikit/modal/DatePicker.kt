@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.uikit.modal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadingGrey
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import java.util.Calendar

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
                    text = stringResource(R.string.common_confirm_action),
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
                    text = stringResource(R.string.common_cancel_action),
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
                    text = stringResource(R.string.transaction_select_date_label),
                    color = White,
                    style = CompanionTheme.typography.bodyBold,
                    modifier = Modifier.padding(
                        PaddingValues(
                            start = CompanionTheme.spacings.spacingE,
                            end = CompanionTheme.spacings.spacingC,
                            top = CompanionTheme.spacings.spacingD
                        )
                    )
                )
            },
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                dayContentColor = White,
                selectedDayContentColor = SunburstGold,
                todayContentColor = FadingGrey,
                weekdayContentColor = White,
            )
        )
    }
}

@Composable
@LunchMoneyPreview
fun LunchDatePickerPreview() {
    CompanionTheme {

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Calendar.getInstance().timeInMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }
            }
        )

        LunchDatePicker(
            datePickerState = datePickerState,
            onDismissRequest = {},
            onDateSelected = {}
        )
    }
}