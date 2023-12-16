@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.features.transactions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.uikit.theme.Body
import com.rodrigolmti.lunch.money.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.uikit.theme.Header
import com.rodrigolmti.lunch.money.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.uikit.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun TransactionDetailBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    transaction: TransactionView,
    onBottomSheetDismissed: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = {
            onBottomSheetDismissed()
        },
        containerColor = MidnightSlate,
        sheetState = sheetState,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                stringResource(R.string.transaction_detail_label),
                style = Header,
                color = White,
            )
            VerticalSpacer(height = 16.dp)
            LunchTextField(
                label = stringResource(R.string.transaction_date_label),
                readOnly = true,
                text = transaction.date,
            )
            VerticalSpacer(height = 8.dp)
            LunchTextField(
                label = stringResource(R.string.transaction_category_label),
                readOnly = true,
                text = transaction.categoryName ?: "-",
            )
            VerticalSpacer(height = 8.dp)
            LunchTextField(
                label = stringResource(R.string.transaction_payee_label),
                readOnly = true,
                text = transaction.payee,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = stringResource(R.string.transaction_amount_label),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = Body,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = formatCurrency(
                    transaction.amount,
                    transaction.currency
                ),
                color = White,
                style = BodyBold,
            )
            VerticalSpacer(height = 8.dp)
            LunchTextField(
                label = stringResource(R.string.transaction_notes_label),
                readOnly = true,
                text = transaction.notes ?: "-",
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = stringResource(R.string.transaction_original_name_label),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = Body,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = transaction.originalName ?: "-",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = White,
                style = BodyBold,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = stringResource(R.string.transaction_paid_from_label),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = SilverLining,
                style = Body,
            )
            VerticalSpacer(height = 8.dp)
            Text(
                text = transaction.assetName ?: "-",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = White,
                style = BodyBold,
            )
            VerticalSpacer(height = 24.dp)
            LunchButton(
                label = stringResource(R.string.close_action),
            ) {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onBottomSheetDismissed()
                    }
                }
            }
            VerticalSpacer(height = 32.dp)
        }
    }
}