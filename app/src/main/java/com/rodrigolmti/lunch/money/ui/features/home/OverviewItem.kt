package com.rodrigolmti.lunch.money.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetOverviewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetStatus
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetType
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.BodyBold
import com.rodrigolmti.lunch.money.ui.theme.SunburstGold
import com.rodrigolmti.lunch.money.ui.theme.White

@Composable
fun OverviewItem(
    overviews: List<AssetOverviewModel>,
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
        state = listState,
    ) {
        items(overviews.size) { index ->

            val overview = overviews[index]

            Column {
                Text(
                    text = "${getAssetTypeLabel(overview.type)} (${overview.assets.size})",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    color = White,
                    style = Body,
                )
                overview.assets.forEach { asset ->
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp,)
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = asset.institutionName ?: asset.name,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start,
                                color = SunburstGold,
                                style = BodyBold,
                            )

                            Text(
                                text = formatCurrency(
                                    asset.balance.toFloat(),
                                    asset.currency
                                ),
                                textAlign = TextAlign.Start,
                                color = White,
                                style = Body,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getAssetTypeLabel(type: AssetType): String {
    return when (type) {
        AssetType.CASH -> "Cash"
        AssetType.INVESTMENT -> "Investment"
        AssetType.LOAN -> "Loan"
        AssetType.CREDIT -> "Credit"
        AssetType.REAL_STATE -> "Real State"
        AssetType.DEPOSITORY -> "Depository"
        AssetType.BROKERAGE -> "Brokerage"
        AssetType.VEHICLE -> "Vehicle"
        AssetType.CRYPTOCURRENCY -> "Cryptocurrency"
        AssetType.EMPLOYEE_COMPENSATION -> "Employee Compensation"
        AssetType.OTHER_LIABILITIES -> "Other Liabilities"
        AssetType.OTHER_ASSETS -> "Other Assets"
    }
}

@Preview
@Composable
fun OverviewItemPreview() {
    OverviewItem(
        overviews = listOf(
            AssetOverviewModel(
                total = 100.0,
                type = AssetType.CASH,
                assets = listOf(
                    AssetModel(
                        id = 1,
                        type = AssetType.CASH,
                        subtypeName = "Cash",
                        name = "Cash",
                        balance = 100.0,
                        balanceAsOf = "2021-01-01",
                        currency = "USD",
                        institutionName = null,
                        status = AssetStatus.ACTIVE,
                    )
                )
            )
        )
    )
}