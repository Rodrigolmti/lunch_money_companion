package com.rodrigolmti.lunch.money.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.ui.theme.Header
import com.rodrigolmti.lunch.money.ui.theme.PassingGrey
import com.rodrigolmti.lunch.money.ui.theme.SunburstGold

@Composable
internal fun LunchAppBar(title: String) {
    Column{
        Text(
            text = title,
            style = Header,
            color = SunburstGold,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
        )

        VerticalSpacer(height = 16.dp)

        Box(modifier = Modifier
            .height(Dp(0.5f))
            .fillMaxWidth()
            .background(PassingGrey),
        )
    }
}

@Preview
@Composable
internal fun LunchAppBarPreview() {
    LunchAppBar("Lunch Money")
}