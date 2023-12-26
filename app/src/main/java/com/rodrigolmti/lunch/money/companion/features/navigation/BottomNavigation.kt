@file:OptIn(ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.features.home.ui.HomeScreen
import com.rodrigolmti.lunch.money.companion.features.home.ui.IHomeViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.ISettingsViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.SettingsScreen
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionView
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionDetailScreen
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionsScreen
import com.rodrigolmti.lunch.money.companion.uikit.components.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.companion.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

internal enum class BottomNavigationRouter {
    HOME,
    TRANSACTIONS,
    SETTINGS,
}

internal val screens = listOf(
    BottomNavigationRouter.HOME,
    BottomNavigationRouter.TRANSACTIONS,
    BottomNavigationRouter.SETTINGS,
)

@Immutable
internal sealed class BottomNavigationUiState {
    data class ShowTransactionDetailBottomSheet(val transaction: TransactionView) :
        BottomNavigationUiState()

    data class ShowErrorBottomSheet(val title: String, val message: String) :
        BottomNavigationUiState()

    data class ShowInformationBottomSheet(val title: String, val message: String) :
        BottomNavigationUiState()

    data object Idle : BottomNavigationUiState()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun BottomNavigation(
    selectedScreen: BottomNavigationRouter,
    onTermsOfUseClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onScreenSelected: (BottomNavigationRouter) -> Unit,
) {
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded })

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

                is BottomNavigationUiState.ShowTransactionDetailBottomSheet -> {
                    BuildTransactionDetailState(state, scope, sheetState)
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
                            icon = { Icon(getIconByRoute(route), contentDescription = null) },
                            label = { Text(getLabelByRoute(route)) },
                            selected = route == selectedScreen,
                            onClick = { onScreenSelected(route) },
                            modifier = Modifier.padding(8.dp),
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

                    TransactionsScreen(uiModel, {
                        state.value = BottomNavigationUiState.ShowTransactionDetailBottomSheet(it)
                        scope.launch { sheetState.show() }
                    }, { title, description ->
                        state.value =
                            BottomNavigationUiState.ShowErrorBottomSheet(title, description)
                        scope.launch { sheetState.show() }
                    })
                }

                BottomNavigationRouter.SETTINGS -> {
                    val uiModel = koinViewModel<ISettingsViewModel>()

                    SettingsScreen(uiModel, {
                        onLogout()
                    }, {
                        onTermsOfUseClick()
                    })
                }

                BottomNavigationRouter.HOME -> {
                    val uiModel = koinViewModel<IHomeViewModel>()

                    HomeScreen(uiModel) { title, description ->
                        state.value =
                            BottomNavigationUiState.ShowErrorBottomSheet(title, description)
                        scope.launch { sheetState.show() }
                    }
                }
            }
        }
    }
}

@Composable
private fun BuildTransactionDetailState(
    state: MutableState<BottomNavigationUiState>,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    val selectedTransaction =
        (state.value as BottomNavigationUiState.ShowTransactionDetailBottomSheet).transaction
    TransactionDetailScreen(
        transaction = selectedTransaction,
        onBottomSheetDismissed = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    state.value = BottomNavigationUiState.Idle
                }
            }
        }
    )
}

@Composable
private fun BuildErrorState(
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    state: MutableState<BottomNavigationUiState>
) {
    BottomSheetComponent(
        title = stringResource(R.string.common_error_title),
        message = stringResource(R.string.transaction_error_message),
    ) {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                state.value = BottomNavigationUiState.Idle
            }
        }
    }
}

private fun getIconByRoute(route: BottomNavigationRouter): ImageVector {
    return when (route) {
        BottomNavigationRouter.TRANSACTIONS -> Icons.Filled.List
        BottomNavigationRouter.SETTINGS -> Icons.Filled.Settings
        BottomNavigationRouter.HOME -> Icons.Filled.Home
    }
}

@Composable
private fun getLabelByRoute(route: BottomNavigationRouter): String {
    return when (route) {
        BottomNavigationRouter.TRANSACTIONS -> stringResource(R.string.bottom_navigation_transactions)
        BottomNavigationRouter.SETTINGS -> stringResource(R.string.bottom_navigation_settings)
        BottomNavigationRouter.HOME -> stringResource(R.string.bottom_navigation_home)
    }
}