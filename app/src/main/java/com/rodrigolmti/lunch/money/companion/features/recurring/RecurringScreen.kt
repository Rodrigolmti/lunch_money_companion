package com.rodrigolmti.lunch.money.companion.features.recurring

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme

@Composable
@LunchMoneyPreview
private fun RecurringScreenPreview(
    @PreviewParameter(RecurringUIModelProvider::class) uiModel: IRecurringUIModel
) {
    CompanionTheme {
        RecurringScreen(uiModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun RecurringScreen(
    uiModel: IRecurringUIModel = DummyIRecurringUIModel(),
    onError: (String, String) -> Unit = { _, _ -> },
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            LunchAppBar(stringResource(R.string.recurring_title))
        }
    ) {
        when (viewState) {
            is RecurringUiState.Loading -> {
                Center {
                    LunchLoading()
                }
            }

            is RecurringUiState.Error -> {
                EmptyState(
                    stringResource(R.string.common_error_title),
                )

                onError(
                    stringResource(R.string.common_error_title),
                    stringResource(R.string.home_error_message)
                )
            }

            is RecurringUiState.Success -> {
                val values = (viewState as RecurringUiState.Success).values

                Column(
                    modifier = Modifier
                        .padding(top = CompanionTheme.spacings.spacingI)
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            bottom = CompanionTheme.spacings.spacingJ,
                            top = CompanionTheme.spacings.spacingD
                        ),
                        verticalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingB),
                        modifier = Modifier
                            .padding(
                                start = CompanionTheme.spacings.spacingD,
                                end = CompanionTheme.spacings.spacingD,
                            ),
                        state = listState,
                    ) {
                        items(
                            count = values.size,
                            key = { index -> values[index].id },
                        ) { index ->

                            val item = values[index]

                            RecurringItem(item)
                        }
                    }
                }
            }
        }
    }
}