package com.rodrigolmti.lunch.money.companion.features.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rodrigolmti.lunch.money.companion.application.main.IMainActivityViewModel
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.terms.WebViewScreen
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail.ITransactionDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail.TransactionsDetailScreen
import org.koin.androidx.compose.koinViewModel

internal const val authenticationRoute = "/authentication"
internal const val dashboardRoute = "/dashboard"
internal const val webviewRouter = "/webview?url={url}"
internal const val transactionDetailRouter = "/transaction?id={id}"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun NavigationGraph(
    navController: NavHostController,
    viewModel: IMainActivityViewModel
) {
    var selectedScreen by remember { mutableStateOf(screens.first()) }
    val isUserAuthenticated = viewModel.isUserAuthenticated()

    NavHost(
        navController,
        startDestination = if (isUserAuthenticated) dashboardRoute else authenticationRoute
    ) {
        composable(authenticationRoute) {
            val uiModel = koinViewModel<IAuthenticationViewModel>()

            AuthenticationScreen(
                uiModel = uiModel,
                onUserAuthenticated = { navController.navigate(dashboardRoute) }
            )
        }
        composable(dashboardRoute) {
            BottomNavigation(
                selectedScreen = selectedScreen,
                onTermsOfUseClick = {
                    navController.navigate(webviewRouter.replace("{url}", it))
                },
                onLogout = {
                    navController.navigate(authenticationRoute) {
                        popUpTo(authenticationRoute) {
                            inclusive = true
                        }
                    }
                },
                onScreenSelected = { selectedScreen = it },
                onTransactionSelected = {
                    navController.navigate(transactionDetailRouter.replace("{id}", it.toString()))
                })
        }
        composable(
            route = transactionDetailRouter,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val uiModel = koinViewModel<ITransactionDetailViewModel>()

            navBackStackEntry.arguments?.getInt("id")?.let {
                TransactionsDetailScreen(id = it, uiModel = uiModel) {
                    navController.navigateUp()
                }
            }
        }
        composable(
            route = webviewRouter,
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let {
                WebViewScreen(url = it) {
                    navController.navigateUp()
                }
            }
        }
    }
}