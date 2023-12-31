package com.rodrigolmti.lunch.money.companion.features.home.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetModelView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetTypeView
import com.rodrigolmti.lunch.money.companion.features.home.model.fakeAssetOverviewView
import com.rodrigolmti.lunch.money.companion.uikit.theme.Body
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun OverviewItem(
    overviews: List<AssetOverviewView>,
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
                            .padding(start = 16.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = asset.name,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start,
                                color = SunburstGold,
                                style = BodyBold,
                            )

                            Text(
                                text = formatItemValue(asset),
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

private fun formatItemValue(asset: AssetModelView): String {
    if (asset.type == AssetTypeView.CRYPTOCURRENCY) {
        return asset.balance.toString()
    }

    return formatCurrency(
        asset.balance.toFloat(),
        asset.currency
    )
}

@Composable
private fun getAssetTypeLabel(type: AssetTypeView): String {
    return when (type) {
        AssetTypeView.CASH -> stringResource(R.string.asset_type_cash)
        AssetTypeView.INVESTMENT -> stringResource(R.string.asset_type_investment)
        AssetTypeView.LOAN -> stringResource(R.string.asset_type_loan)
        AssetTypeView.CREDIT -> stringResource(R.string.asset_type_credit)
        AssetTypeView.REAL_STATE -> stringResource(R.string.asset_type_real_state)
        AssetTypeView.DEPOSITORY -> stringResource(R.string.asset_type_depository)
        AssetTypeView.BROKERAGE -> stringResource(R.string.asset_type_brokerage)
        AssetTypeView.VEHICLE -> stringResource(R.string.asset_type_vehicle)
        AssetTypeView.CRYPTOCURRENCY -> stringResource(R.string.asset_type_cryptocurrency)
        AssetTypeView.EMPLOYEE_COMPENSATION -> stringResource(R.string.asset_type_employee_compensation)
        AssetTypeView.OTHER_LIABILITIES -> stringResource(R.string.asset_type_other_liabilities)
        AssetTypeView.UNKNOWN, AssetTypeView.OTHER_ASSETS -> stringResource(R.string.asset_type_other_assets)
    }
}

@Composable
@LunchMoneyPreview
private fun OverviewItemPreview() {
    OverviewItem(
        overviews = listOf(
            fakeAssetOverviewView(),
            fakeAssetOverviewView(),
            fakeAssetOverviewView()
        )
    )
}