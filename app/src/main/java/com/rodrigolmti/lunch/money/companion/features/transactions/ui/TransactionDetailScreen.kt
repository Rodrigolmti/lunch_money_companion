package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun TransactionDetailScreen(
    transaction: TransactionView,
    onBottomSheetDismissed: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(CompanionTheme.spacings.spacingD),
        verticalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingD)
    ) {
        Text(
            stringResource(R.string.transaction_detail_label),
            style = CompanionTheme.typography.header,
            color = White,
        )
        LunchTextField(
            label = stringResource(R.string.transaction_date_label),
            readOnly = true,
            text = transaction.date,
        )
        LunchTextField(
            label = stringResource(R.string.transaction_category_label),
            readOnly = true,
            text = transaction.categoryName ?: "-",
        )
        LunchTextField(
            label = stringResource(R.string.transaction_payee_label),
            readOnly = true,
            text = transaction.payee,
        )
        Text(
            text = stringResource(R.string.transaction_amount_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = formatCurrency(
                transaction.amount,
                transaction.currency
            ),
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        LunchTextField(
            label = stringResource(R.string.transaction_notes_label),
            readOnly = true,
            text = transaction.notes ?: "-",
        )
        Text(
            text = stringResource(R.string.transaction_original_name_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = transaction.originalName ?: "-",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        Text(
            text = stringResource(R.string.transaction_paid_from_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = transaction.assetName ?: "-",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        LunchButton(
            label = stringResource(R.string.close_action),
        ) {
            onBottomSheetDismissed()
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
    }
}