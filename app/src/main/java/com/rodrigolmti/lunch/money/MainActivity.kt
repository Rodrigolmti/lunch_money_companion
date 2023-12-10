package com.rodrigolmti.lunch.money

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.TransactionsScreen
import com.rodrigolmti.lunch.money.ui.theme.ContentBrand
import com.rodrigolmti.lunch.money.ui.theme.ContentDefault
import com.rodrigolmti.lunch.money.ui.theme.ContentText
import com.rodrigolmti.lunch.money.ui.theme.LunchMoneyTheme
import org.koin.androidx.compose.koinViewModel

abstract class IMainActivityViewModel : ViewModel() {
    abstract val isUserAuthenticated: () -> Boolean
}

class MainActivityViewModel(
    override val isUserAuthenticated: () -> Boolean
) : IMainActivityViewModel()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = koinViewModel<IMainActivityViewModel>()

            LunchMoneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(viewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationGraph(viewModel: IMainActivityViewModel) {

    val screens = listOf(
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
                        backgroundColor = ContentDefault,
                    ) {
                        screens.forEach { route ->
                            BottomNavigationItem(
                                icon = { Icon(getIconByRoute(route), contentDescription = null) },
                                label = { Text(getLabelByRoute(route)) },
                                selected = route == selectedScreen,
                                onClick = { selectedScreen = route },
                                modifier = Modifier.padding(8.dp),
                                selectedContentColor = ContentBrand,
                                unselectedContentColor = ContentText,
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
                }
            }
        }
    }
}

private fun getIconByRoute(route: BottomNavigationRouter): ImageVector {
    return when (route) {
        BottomNavigationRouter.TRANSACTIONS -> Icons.Filled.List
        BottomNavigationRouter.SETTINGS -> Icons.Filled.Settings
    }
}

private fun getLabelByRoute(route: BottomNavigationRouter): String {
    return when (route) {
        BottomNavigationRouter.TRANSACTIONS -> "Transactions"
        BottomNavigationRouter.SETTINGS -> "Settings"
    }
}

enum class BottomNavigationRouter {
    TRANSACTIONS,
    SETTINGS,
}