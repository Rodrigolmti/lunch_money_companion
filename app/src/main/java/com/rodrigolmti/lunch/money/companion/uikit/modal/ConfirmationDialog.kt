package com.rodrigolmti.lunch.money.companion.uikit.modal

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
@LunchMoneyPreview
fun ConfirmationDialogPreview() {
    CompanionTheme {
        ConfirmationDialog(
            dialogTitle = "This is a title",
            dialogText = "This is a description, for a dialog.",
        )
    }
}

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    onFinish: () -> Unit = {},
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                color = White,
                style = CompanionTheme.typography.bodyBold,
            )
        },
        text = {
            Text(
                text = dialogText,
                color = SilverLining,
                style = CompanionTheme.typography.bodyBold,
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                    onFinish()
                }
            ) {
                Text(
                    stringResource(id = R.string.common_yes_action),
                    color = FadedBlood
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                    onFinish()
                }
            ) {
                Text(
                    stringResource(id = R.string.common_no_action),
                    color = SilverLining
                )
            }
        }
    )
}