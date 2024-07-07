@file:OptIn(ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetScreen
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetView
import com.rodrigolmti.lunch.money.companion.features.budget.IBudgetViewModel
import com.rodrigolmti.lunch.money.companion.features.home.ui.HomeScreen
import com.rodrigolmti.lunch.money.companion.features.home.ui.IHomeViewModel
import com.rodrigolmti.lunch.money.companion.features.recurring.IRecurringViewModel
import com.rodrigolmti.lunch.money.companion.features.recurring.RecurringScreen
import com.rodrigolmti.lunch.money.companion.features.settings.ISettingsViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.SettingsScreen
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionsScreen
import com.rodrigolmti.lunch.money.companion.uikit.modal.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

internal val screens = listOf(
    BottomNavigationRouter.HOME,
    BottomNavigationRouter.TRANSACTIONS,
    BottomNavigationRouter.RECURRING,
    BottomNavigationRouter.BUDGET,
    BottomNavigationRouter.SETTINGS,
)

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun BottomNavigation(
    selectedScreen: BottomNavigationRouter,
    onTermsOfUseClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onScreenSelected: (BottomNavigationRouter) -> Unit,
    onTransactionSelected: (Long) -> Unit,
    onTransactionSummaryClick: () -> Unit = {},
    onAnalyzeClick: () -> Unit = {},
    onBreakdownClick: () -> Unit = {},
    onWhatsNewClick: () -> Unit = {},
    onBudgetItemClick: (BudgetView) -> Unit,
) {
    val sheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { true },
            skipHalfExpanded = true
        )

    val state =
        remember { mutableStateOf<BottomNavigationUiState>(BottomNavigationUiState.Idle) }

    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when (state.value) {
                BottomNavigationUiState.Idle -> {
                    // no-op
                }

                is BottomNavigationUiState.ShowErrorBottomSheet -> {
                    BuildErrorState(scope, sheetState, state)
                }

                is BottomNavigationUiState.ShowInformationBottomSheet -> {

                    val information =
                        (state.value as BottomNavigationUiState.ShowInformationBottomSheet)

                    BottomSheetComponent(
                        title = information.title,
                        message = information.message,
                    ) {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                state.value = BottomNavigationUiState.Idle
                            }
                        }
                    }
                }

                is BottomNavigationUiState.ShowDonationBottomSheet -> {
                    DonationBottomSheet {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                state.value = BottomNavigationUiState.Idle
                            }
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxHeight(),
        sheetBackgroundColor = MidnightSlate,
        sheetShape = MaterialTheme.shapes.medium,
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = CharcoalMist,
                    contentColor = SunburstGold,
                ) {
                    screens.forEach { route ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    getIconByRoute(route), contentDescription = null,
                                    modifier = Modifier
                                        .size(CompanionTheme.spacings.spacingE),
                                )
                            },
                            label = {
                                Text(
                                    getLabelByRoute(route),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                )
                            },
                            selected = route == selectedScreen,
                            onClick = { onScreenSelected(route) },
                            modifier = Modifier.padding(CompanionTheme.spacings.spacingB),
                            selectedContentColor = SunburstGold,
                            unselectedContentColor = GraphiteWhisper,
                        )
                    }
                }
            }
        ) {
            when (selectedScreen) {
                BottomNavigationRouter.TRANSACTIONS -> {
                    val uiModel = koinViewModel<ITransactionsViewModel>()

                    TransactionsScreen(
                        uiModel = uiModel,
                        onTransactionItemClick = {
                            onTransactionSelected(it)
                        },
                        onError = { title, description ->
                            updateBottomSheetState(
                                state,
                                BottomNavigationUiState.ShowErrorBottomSheet(title, description),
                                sheetState,
                                scope
                            )
                        },
                        onTransactionSummaryClick = {
                            onTransactionSummaryClick()
                        },
                    )
                }

                BottomNavigationRouter.SETTINGS -> {
                    val uiModel = koinViewModel<ISettingsViewModel>()

                    SettingsScreen(
                        uiModel,
                        onAnalyzeClick = {
                            onAnalyzeClick()
                        },
                        onLogout = {
                            onLogout()
                        },
                        onTermsOfUseClick = {
                            onTermsOfUseClick()
                        },
                        onDonationClick = {
                            updateBottomSheetState(
                                state,
                                BottomNavigationUiState.ShowDonationBottomSheet,
                                sheetState,
                                scope
                            )
                        },
                        onWhatsNewClick = {
                            onWhatsNewClick()
                        },
                    )
                }

                BottomNavigationRouter.HOME -> {
                    val uiModel = koinViewModel<IHomeViewModel>()

                    HomeScreen(
                        uiModel = uiModel,
                        onError = { title, description ->
                            updateBottomSheetState(
                                state,
                                BottomNavigationUiState.ShowInformationBottomSheet(title, description),
                                sheetState,
                                scope
                            )
                        },
                        onBreakdownClick = onBreakdownClick,
                    )
                }

                BottomNavigationRouter.BUDGET -> {
                    val uiModel = koinViewModel<IBudgetViewModel>()

                    BudgetScreen(uiModel, { title, description ->
                        updateBottomSheetState(
                            state,
                            BottomNavigationUiState.ShowInformationBottomSheet(title, description),
                            sheetState,
                            scope
                        )
                    }, {
                        onBudgetItemClick(it)
                    },)
                }

                BottomNavigationRouter.RECURRING -> {
                    val uiModel = koinViewModel<IRecurringViewModel>()

                    RecurringScreen(uiModel) { title, description ->
                        updateBottomSheetState(
                            state,
                            BottomNavigationUiState.ShowInformationBottomSheet(title, description),
                            sheetState,
                            scope
                        )
                    }
                }
            }
        }
    }
}

private fun updateBottomSheetState(
    state: MutableState<BottomNavigationUiState>,
    newState: BottomNavigationUiState,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
) {
    state.value = newState
    scope.launch { sheetState.show() }
}

@Composable
private fun BuildErrorState(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    state: MutableState<BottomNavigationUiState>
) {
    BottomSheetComponent(
        title = stringResource(R.string.common_error_title),
        message = stringResource(R.string.transactions_error_message),
    ) {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                state.value = BottomNavigationUiState.Idle
            }
        }
    }
}

@Composable
private fun getIconByRoute(route: BottomNavigationRouter): Painter {
    val icon = when (route) {
        BottomNavigationRouter.TRANSACTIONS -> R.drawable.ic_transactions
        BottomNavigationRouter.SETTINGS -> R.drawable.ic_settings
        BottomNavigationRouter.HOME -> R.drawable.ic_home
        BottomNavigationRouter.BUDGET -> R.drawable.ic_budget
        BottomNavigationRouter.RECURRING -> R.drawable.ic_recurring
    }

    return painterResource(id = icon)
}

@Composable
private fun getLabelByRoute(route: BottomNavigationRouter): String {
    return when (route) {
        BottomNavigationRouter.TRANSACTIONS -> stringResource(R.string.bottom_navigation_transactions)
        BottomNavigationRouter.SETTINGS -> stringResource(R.string.bottom_navigation_settings)
        BottomNavigationRouter.HOME -> stringResource(R.string.bottom_navigation_home)
        BottomNavigationRouter.BUDGET -> stringResource(R.string.bottom_navigation_budget)
        BottomNavigationRouter.RECURRING -> stringResource(R.string.bottom_navigation_recurring)
    }
}