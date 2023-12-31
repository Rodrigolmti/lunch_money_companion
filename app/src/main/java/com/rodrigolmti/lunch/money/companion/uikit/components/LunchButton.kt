package com.rodrigolmti.lunch.money.companion.uikit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.companion.uikit.theme.LunchMoneyCompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.NightSkyMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold

@Composable
fun LunchButton(
    label: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = NightSkyMist,
            containerColor = SunburstGold
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
    ) {

        if (isLoading) {
            LunchLoading()
        } else {
            Text(
                label,
                style = BodyBold,
                color = MidnightSlate
            )
        }
    }
}

@Composable
@LunchMoneyPreview
private fun ButtonPreview() {
    LunchMoneyCompanionTheme {
        Column {
            LunchButton(label = "Authenticate") {}
            VerticalSpacer(height = 8.dp)
            LunchButton(label = "Authenticate", isLoading = true) {}
        }
    }
}
