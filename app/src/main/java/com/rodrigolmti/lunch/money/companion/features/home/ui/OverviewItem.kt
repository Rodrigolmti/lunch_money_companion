package com.rodrigolmti.lunch.money.companion.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetModelView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetTypeView
import com.rodrigolmti.lunch.money.companion.features.home.model.fakeAssetOverviewView
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

@Composable
internal fun OverviewItem(
    overviews: List<AssetOverviewView>,
) {

    Column(
        modifier = Modifier
            .padding(CompanionTheme.spacings.spacingD),
    ) {
        Text(
            stringResource(R.string.home_account_label),
            color = White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompanionTheme.typography.body
        )
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        Divider()
        VerticalSpacer(height = CompanionTheme.spacings.spacingB)
        overviews.forEach { overview ->
            Text(
                text = "${getAssetTypeLabel(overview.type)} (${overview.assets.size})",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = White,
                style = CompanionTheme.typography.body,
            )
            overview.assets.forEach { asset ->
                Column(
                    modifier = Modifier
                        .padding(start = CompanionTheme.spacings.spacingD)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = asset.name,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start,
                            color = SunburstGold,
                            style = CompanionTheme.typography.bodyBold,
                        )

                        Text(
                            text = formatItemValue(asset),
                            textAlign = TextAlign.Start,
                            color = White,
                            style = CompanionTheme.typography.body,
                        )
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