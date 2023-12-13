package com.rodrigolmti.lunch.money.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigolmti.lunch.money.IMainActivityViewModel
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.home.HomeScreen
import com.rodrigolmti.lunch.money.ui.features.home.IHomeViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.TransactionsScreen
import com.rodrigolmti.lunch.money.ui.theme.CharcoalMist
import com.rodrigolmti.lunch.money.ui.theme.GraphiteWhisper
import com.rodrigolmti.lunch.money.ui.theme.SunburstGold
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationGraph(viewModel: IMainActivityViewModel) {

    val screens = listOf(
        BottomNavigationRouter.HOME,
        BottomNavigationRouter.TRANSACTIONS,
        BottomNavigationRouter.SETTINGS,
    )

    var selectedScreen by remember { mutableStateOf(screens.first()) }
    val isUserAuthenticated = viewModel.isUserAuthenticated()
    val navController = rememberNavController()

    val authenticationRoute = "/authentication"
    val dashboardRoute = "/dashboard"

    NavHost(
        navController,
        startDestination = if (isUserAuthenticated) dashboardRoute else authenticationRoute
    ) {
        composable(authenticationRoute) {
            val uiModel = koinViewModel<IAuthenticationViewModel>()

            AuthenticationScreen(uiModel) {
                navController.navigate(dashboardRoute)
            }
        }
        composable(dashboardRoute) {
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
                                onClick = { selectedScreen = route },
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

                    }

                    BottomNavigationRouter.HOME -> {
                        val uiModel = koinViewModel<IHomeViewModel>()

                        HomeScreen(uiModel)
                    }
                }
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

private fun getLabelByRoute(route: BottomNavigationRouter): String {
    return when (route) {
        BottomNavigationRouter.TRANSACTIONS -> "Transactions"
        BottomNavigationRouter.SETTINGS -> "Settings"
        BottomNavigationRouter.HOME -> "Home"
    }
}

enum class BottomNavigationRouter {
    HOME,
    TRANSACTIONS,
    SETTINGS,
}