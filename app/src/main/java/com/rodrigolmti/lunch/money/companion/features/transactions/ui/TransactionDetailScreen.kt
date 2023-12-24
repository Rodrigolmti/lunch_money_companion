package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.Header
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun TransactionDetailScreen(
    transaction: TransactionView,
    onBottomSheetDismissed: () -> Unit = {},
) {
    Column(
        modifier = Modifier
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
            onBottomSheetDismissed()
        }
        VerticalSpacer(height = 32.dp)
    }
}