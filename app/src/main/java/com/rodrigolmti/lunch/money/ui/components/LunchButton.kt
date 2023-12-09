package com.rodrigolmti.lunch.money.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.ui.theme.BackgroundDefault
import com.rodrigolmti.lunch.money.ui.theme.BodyBold
import com.rodrigolmti.lunch.money.ui.theme.LunchMoneyTheme

@Composable
fun LunchButton(
    label: String,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                modifier = Modifier.size(25.dp),
                color = Color.White,
                trackColor = BackgroundDefault,
            )
        } else {
            Text(
                label,
                style = BodyBold,
                color = BackgroundDefault
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
