package com.rodrigolmti.lunch.money.ui.features.transactions.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.ui.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.TransactionStatus
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.BodyBold
import com.rodrigolmti.lunch.money.ui.theme.CharcoalMist
import com.rodrigolmti.lunch.money.ui.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.ui.theme.SilverLining
import com.rodrigolmti.lunch.money.ui.theme.TropicalLagoon
import com.rodrigolmti.lunch.money.ui.theme.White

@Composable
internal fun TransactionItem(
    transaction: TransactionModel,
    onItemClicked: () -> Unit = {},
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist
        ),
        border = BorderStroke(
            width = Dp.Hairline,
            color = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 8.dp,
                    bottom = 16.dp
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
                        style = Body,
                    )

                    HorizontalSpacer(8.dp)

                    Text(
                        text = formatCurrency(
                            transaction.amount,
                            transaction.currency
                        ),
                        color = White,
                        style = BodyBold,
                    )
                }

                VerticalSpacer(height = 8.dp)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = transaction.date,
                        color = White,
                        style = Body,
                    )

                    HorizontalSpacer(8.dp)

                    Text(
                        text = getTransactionStatusLabel(transaction.status),
                        color = getTransactionStatusColor(transaction.status),
                        style = Body,
                    )
                }
            }

            HorizontalSpacer(width = 8.dp)

            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = SilverLining,
            )
        }
    }
}

private fun getTransactionStatusColor(status: TransactionStatus): Color = when (status) {
    TransactionStatus.CLEARED -> EmeraldSpring
    TransactionStatus.RECURRING -> TropicalLagoon
    TransactionStatus.RECURRING_SUGGESTED -> TropicalLagoon
    TransactionStatus.PENDING -> Color.Red
    TransactionStatus.UNKNOWN -> SilverLining
}

@Composable
private fun getTransactionStatusLabel(status: TransactionStatus): String = when (status) {
    TransactionStatus.CLEARED -> stringResource(R.string.transaction_label_cleared)
    TransactionStatus.RECURRING -> stringResource(R.string.transaction_label_recurring)
    TransactionStatus.RECURRING_SUGGESTED -> stringResource(R.string.transaction_label_recurring_suggested)
    TransactionStatus.PENDING -> stringResource(R.string.transaction_label_pending)
    TransactionStatus.UNKNOWN -> stringResource(R.string.transaction_label_unknown)
}