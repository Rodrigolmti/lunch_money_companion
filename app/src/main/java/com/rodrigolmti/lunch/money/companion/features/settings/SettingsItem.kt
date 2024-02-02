package com.rodrigolmti.lunch.money.companion.features.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.theme.FadedBlood
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining

@Composable
internal fun SettingsItem(
    label: String,
    description: String? = null,
    icon: Painter,
    color: Color = SilverLining,
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(
                start = CompanionTheme.spacings.spacingD,
                end = CompanionTheme.spacings.spacingD,
                top = CompanionTheme.spacings.spacingC,
                bottom = CompanionTheme.spacings.spacingC,
            ),
    ) {
        Icon(
            painter = icon,
            tint = color,
            modifier = Modifier
                .size(CompanionTheme.spacings.spacingE),
            contentDescription = ""
        )

        HorizontalSpacer(CompanionTheme.spacings.spacingB)

        Column {
            Text(
                text = label,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = color,
                style = CompanionTheme.typography.bodyBold,
            )

            description?.let {
                VerticalSpacer(CompanionTheme.spacings.spacingA)
                Text(
                    text = description,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    color = color,
                    overflow = TextOverflow.Ellipsis,
                    style = CompanionTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
@LunchMoneyPreview
private fun SettingsItemPreview() {
    CompanionTheme {
        Column {
            SettingsItem(
                label = "Terms of Use",
                description = "1.0.0 made with ❤️ by Rodrigo Lopes Martins",
                icon = painterResource(id = R.drawable.ic_btc)
            )
            SettingsItem(
                label = "Terms of Use",
                icon = painterResource(id = R.drawable.ic_btc)
            )
            SettingsItem(
                label = "Terms of Use",
                color = FadedBlood,
                icon = painterResource(id = R.drawable.ic_btc)
            )
        }
    }
}