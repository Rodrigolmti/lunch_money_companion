@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.features.transactions.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionStatusView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.fakeTransactionView
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.TropicalLagoon
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun TransactionItem(
    transaction: TransactionView,
    onItemClicked: () -> Unit = {},
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist,
        ),
        border = BorderStroke(
            width = Dp.Hairline,
            color = Color.Black
        ),
        onClick = {
            onItemClicked()
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = CompanionTheme.spacings.spacingD,
                    start = CompanionTheme.spacings.spacingD,
                    end = CompanionTheme.spacings.spacingB,
                    bottom = CompanionTheme.spacings.spacingD
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = transaction.payee,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(1f),
                        color = White,
                        style = CompanionTheme.typography.body,
                    )

                    HorizontalSpacer(CompanionTheme.spacings.spacingB)

                    Text(
                        text = formatCurrency(
                            transaction.amount,
                            transaction.currency
                        ),
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }

                VerticalSpacer(height = CompanionTheme.spacings.spacingB)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = transaction.date,
                        color = White,
                        style = CompanionTheme.typography.body,
                    )

                    HorizontalSpacer(CompanionTheme.spacings.spacingB)

                    Text(
                        text = getTransactionStatusLabel(transaction.status),
                        color = getTransactionStatusColor(transaction.status),
                        style = CompanionTheme.typography.body,
                    )
                }
            }

            HorizontalSpacer(width = CompanionTheme.spacings.spacingB)

            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = SilverLining,
            )
        }
    }
}

@Composable
@LunchMoneyPreview
fun TransactionItemPreview() {
    Column {
        TransactionItem(fakeTransactionView(status = TransactionStatusView.CLEARED)) {}
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        TransactionItem(fakeTransactionView(status = TransactionStatusView.PENDING)) {}
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        TransactionItem(fakeTransactionView(status = TransactionStatusView.UNKNOWN)) {}
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        TransactionItem(fakeTransactionView(status = TransactionStatusView.RECURRING)) {}
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        TransactionItem(fakeTransactionView(status = TransactionStatusView.UNCLEARED)) {}
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        TransactionItem(fakeTransactionView(status = TransactionStatusView.RECURRING_SUGGESTED)) {}
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        TransactionItem(fakeTransactionView(status = TransactionStatusView.DELETE_PENDING)) {}
    }
}

private fun getTransactionStatusColor(status: TransactionStatusView): Color = when (status) {
    TransactionStatusView.CLEARED -> EmeraldSpring
    TransactionStatusView.RECURRING -> TropicalLagoon
    TransactionStatusView.RECURRING_SUGGESTED -> TropicalLagoon
    TransactionStatusView.PENDING, TransactionStatusView.DELETE_PENDING -> FadedBlood
    TransactionStatusView.UNCLEARED, TransactionStatusView.UNKNOWN -> SilverLining
}

@Composable
private fun getTransactionStatusLabel(status: TransactionStatusView): String = when (status) {
    TransactionStatusView.CLEARED -> stringResource(R.string.transaction_label_cleared)
    TransactionStatusView.RECURRING -> stringResource(R.string.transaction_label_recurring)
    TransactionStatusView.RECURRING_SUGGESTED -> stringResource(R.string.transaction_label_recurring_suggested)
    TransactionStatusView.PENDING -> stringResource(R.string.transaction_label_pending)
    TransactionStatusView.UNKNOWN -> stringResource(R.string.transaction_label_unknown)
    TransactionStatusView.UNCLEARED -> stringResource(R.string.transaction_label_uncleared)
    TransactionStatusView.DELETE_PENDING -> stringResource(R.string.transaction_pending_delete)
}