package com.rodrigolmti.lunch.money.features.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.R
import com.rodrigolmti.lunch.money.features.home.ui.HomeScreen
import com.rodrigolmti.lunch.money.features.home.ui.IHomeViewModel
import com.rodrigolmti.lunch.money.features.settings.SettingsScreen
import com.rodrigolmti.lunch.money.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.features.transactions.ui.TransactionsScreen
import com.rodrigolmti.lunch.money.uikit.theme.CharcoalMist
import com.rodrigolmti.lunch.money.uikit.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.uikit.theme.SunburstGold
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun BottomNavigation(
    selectedScreen: BottomNavigationRouter,
    onLogoutRequested: () -> Unit = {},
    onScreenSelected: (BottomNavigationRouter) -> Unit,
) {
    Scaffold(
        bottomBar = {
            androidx.compose.material.BottomNavigation(
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

                TransactionsScreen(uiModel)
            }

            BottomNavigationRouter.SETTINGS -> {
                SettingsScreen {
                    onLogoutRequested()
                }
            }

            BottomNavigationRouter.HOME -> {
                val uiModel = koinViewModel<IHomeViewModel>()

                HomeScreen(uiModel)
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