@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.uikit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.uikit.theme.Body
import com.rodrigolmti.lunch.money.uikit.theme.Header
import com.rodrigolmti.lunch.money.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.uikit.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun ErrorBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    title: String,
    message: String,
    onBottomSheetDismissed: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = {
            onBottomSheetDismissed()
        },
        containerColor = MidnightSlate,
        sheetState = sheetState,
        shape = MaterialTheme.shapes.medium,
    ) {
        ErrorComponent(title, message) {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onBottomSheetDismissed()
                }
            }
        }
    }
}

@Composable
fun ErrorComponent(
    title: String,
    message: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    onBottomSheetDismissed: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            title,
            style = Header,
            color = White,
        )
        VerticalSpacer(height = 16.dp)
        Text(
            message,
            style = Body,
            color = White,
        )
        VerticalSpacer(height = 24.dp)
        LunchButton(
            label = "OK",
        ) {
            onBottomSheetDismissed()
        }
        VerticalSpacer(height = 32.dp)
    }
}