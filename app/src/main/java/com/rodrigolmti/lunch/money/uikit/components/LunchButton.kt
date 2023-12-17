package com.rodrigolmti.lunch.money.uikit.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.uikit.theme.BodyBold
import com.rodrigolmti.lunch.money.uikit.theme.LunchMoneyTheme

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
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ButtonPreview() {
    LunchMoneyTheme {
        Column {
            LunchButton(label = "Authenticate") {}
            VerticalSpacer(height = 8.dp)
            LunchButton(label = "Authenticate", isLoading = true) {}
        }
    }
}
