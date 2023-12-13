@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.ui.features.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.ErrorBottomSheet
import com.rodrigolmti.lunch.money.ui.components.LunchLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val DummyIHomeUIModel = object : IHomeUIModel {
    override val viewState: StateFlow<HomeUiState> =
        MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    override fun getAccountOverview() {
        // no-op
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(uiModel: IHomeUIModel = DummyIHomeUIModel) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    var showErrorBottomSheet by remember { mutableStateOf(false) }
    val errorSheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    Scaffold {

        when (viewState) {
            is HomeUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            is HomeUiState.Error -> {
                ErrorBottomSheet(
                    sheetState = errorSheetState,
                    scope = scope,
                    title = stringResource(R.string.common_error_title),
                    message = stringResource(R.string.home_error_message)
                ) {
                    showErrorBottomSheet = false
                }
            }

            is HomeUiState.Success -> {

            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}