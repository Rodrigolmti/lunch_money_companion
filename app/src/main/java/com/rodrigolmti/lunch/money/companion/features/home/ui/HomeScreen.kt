@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigolmti.lunch.money.companion.features.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.ErrorState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val DummyIHomeUIModel = object : IHomeUIModel {
    override val viewState: StateFlow<HomeUiState> =
        MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    override fun getAccountOverview() {
        // no-op
    }

    override fun onRefresh() {
        // no-op
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun HomeScreen(
    uiModel: IHomeUIModel = DummyIHomeUIModel,
    onError: (String, String) -> Unit = { _, _ -> },
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            LunchAppBar(stringResource(R.string.home_title)) {
                IconButton(onClick = {
                    uiModel.onRefresh()
                }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
            }
        }
    ) {

        when (viewState) {
            is HomeUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            is HomeUiState.Error -> {
                ErrorState(
                    stringResource(R.string.common_error_title),
                )

                onError(
                    stringResource(R.string.common_error_title),
                    stringResource(R.string.home_error_message)
                )
            }

            is HomeUiState.Success -> {
                val overviews = (viewState as HomeUiState.Success).overview

                BuildSuccessState(
                    listState,
                    overviews
                )
            }
        }
    }
}

@Composable
private fun BuildSuccessState(
    listState: LazyListState,
    overviews: List<AssetOverviewView>
) {
    Column(
        modifier = Modifier
            .padding(top = 64.dp)
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = CharcoalMist
            ),
            border = BorderStroke(
                width = Dp.Hairline,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OverviewItem(
                listState = listState,
                overviews = overviews
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}