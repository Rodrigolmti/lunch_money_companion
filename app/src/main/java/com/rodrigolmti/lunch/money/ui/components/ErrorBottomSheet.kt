@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.Header
import com.rodrigolmti.lunch.money.ui.theme.MidnightSlate
import com.rodrigolmti.lunch.money.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun ErrorBottomSheet(
    bottomSheetState: SheetState,
    coroutineScope: CoroutineScope,
    title: String,
    message: String,
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet = false
        },
        containerColor = MidnightSlate,
        sheetState = bottomSheetState,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
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
                coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }
            VerticalSpacer(height = 32.dp)
        }
    }
}