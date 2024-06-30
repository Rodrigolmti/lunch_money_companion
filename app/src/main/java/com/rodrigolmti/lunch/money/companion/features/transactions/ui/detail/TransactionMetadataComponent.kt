package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionMetadataView
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.EmeraldSpring
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadingGrey
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
fun TransactionMetadataComponent(
    view: TransactionMetadataView,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CharcoalMist
        ),
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = Dp.Hairline,
            color = Color.Black
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                top = CompanionTheme.spacings.spacingD,
                start = CompanionTheme.spacings.spacingD,
                end = CompanionTheme.spacings.spacingD,
                bottom = CompanionTheme.spacings.spacingD
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                if (view.logoURL != null) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(view.logoURL)
                            .crossfade(true)
                            .build(),
                        loading = {
                            LunchLoading()
                        },
                        contentDescription = stringResource(R.string.transaction_metadata_image_content_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )

                    HorizontalSpacer(CompanionTheme.spacings.spacingC)
                }

                Text(
                    text = stringResource(R.string.transaction_metadata_metadata_label),
                    overflow = TextOverflow.Ellipsis,
                    color = SunburstGold,
                    style = CompanionTheme.typography.bodyBold,
                )
            }
            view.location?.let {
                VerticalSpacer(height = CompanionTheme.spacings.spacingD)
                Column {
                    Text(
                        text = stringResource(R.string.transaction_metadata_address_label),
                        textAlign = TextAlign.Center,
                        style = CompanionTheme.typography.body,
                        color = White
                    )
                    VerticalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = view.location,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }
            }
            view.merchantName?.let {
                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                Column {
                    Text(
                        text = stringResource(R.string.transaction_metadata_merchant_label),
                        textAlign = TextAlign.Center,
                        style = CompanionTheme.typography.body,
                        color = White
                    )
                    VerticalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = view.merchantName,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }
            }
            view.paymentProcessor?.let {
                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                Column {
                    Text(
                        text = stringResource(R.string.transaction_metadata_payment_processor_label),
                        textAlign = TextAlign.Center,
                        style = CompanionTheme.typography.body,
                        color = White
                    )
                    VerticalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = view.paymentProcessor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }
            }
            if (view.categories.isNotEmpty()) {
                VerticalSpacer(height = CompanionTheme.spacings.spacingC)
                Column {
                    Text(
                        text = stringResource(R.string.transaction_metadata_categories_label),
                        textAlign = TextAlign.Center,
                        style = CompanionTheme.typography.body,
                        color = White
                    )
                    VerticalSpacer(CompanionTheme.spacings.spacingA)
                    Text(
                        text = view.categories.joinToString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = White,
                        style = CompanionTheme.typography.bodyBold,
                    )
                }
            }

            VerticalSpacer(height = CompanionTheme.spacings.spacingC)

            Row {
                Text(
                    text = stringResource(R.string.transaction_metadata_is_pending_label),
                    textAlign = TextAlign.Center,
                    style = CompanionTheme.typography.body,
                    color = White
                )
                HorizontalSpacer(CompanionTheme.spacings.spacingA)
                Text(
                    text = if (view.pending) stringResource(R.string.transaction_metadata_yes_label)
                    else stringResource(
                        R.string.transaction_metadata_no_label
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.weight(1f),
                    color = if (view.pending) FadingGrey else EmeraldSpring,
                    style = CompanionTheme.typography.bodyBold,
                )
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun TransactionMetadataComponentPreview() {
    CompanionTheme {
        Column {

            TransactionMetadataComponent(
                TransactionMetadataView(
                    categories = listOf("Transfer", "Debit"),
                    paymentProcessor = "Google Wallet",
                    logoURL = "https://plaid-merchant-logos.plaid.com/aliexpress_37.png",
                    pending = false,
                    merchantName = "Ali Express",
                    location = "Winnipeg - Manitoba - Canada"
                )
            )

            VerticalSpacer(CompanionTheme.spacings.spacingD)

            TransactionMetadataComponent(
                TransactionMetadataView(
                    categories = emptyList(),
                    paymentProcessor = "Google Wallet",
                    logoURL = "https://plaid-merchant-logos.plaid.com/aliexpress_37.png",
                    pending = true,
                    merchantName = "Ali Express",
                    location = null,
                )
            )
        }
    }
}