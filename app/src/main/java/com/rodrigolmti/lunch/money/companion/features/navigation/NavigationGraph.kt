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
import com.rodrigolmti.lunch.money.companion.core.GITHUB_RELEASES_URL
import com.rodrigolmti.lunch.money.companion.core.PRIVACY_POLICY_URL
import com.rodrigolmti.lunch.money.companion.features.analyze.AnalyzeScreen
import com.rodrigolmti.lunch.money.companion.features.analyze.IAnalyzeViewModel
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.detail.BudgetDetailScreen
import com.rodrigolmti.lunch.money.companion.features.budget.detail.IBudgetDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.webView.WebViewScreen
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail.ITransactionDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail.TransactionsDetailScreen
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary.ITransactionsSummaryViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary.TransactionsSummaryScreen
import org.koin.androidx.compose.koinViewModel

internal const val authenticationRoute = "/authentication"
internal const val dashboardRoute = "/dashboard"
internal const val webViewRouter = "/webView?url={url}"
internal const val transactionDetailRouter = "/transaction?id={id}"
internal const val budgetDetailRouter = "/budget?id={id}"
internal const val transactionSummaryRouter = "/transaction/summary"
internal const val analyzeRouter = "/analyze"

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
                onAnalyzeClick = {
                    navController.navigate(analyzeRouter)
                },
                onTermsOfUseClick = {
                    navController.navigate(webViewRouter.replace("{url}", PRIVACY_POLICY_URL))
                },
                onLogout = {
                    navController.navigate(authenticationRoute) {
                        popUpTo(authenticationRoute) {
                            inclusive = true
                        }
                    }
                },
                onTransactionSummaryClick = {
                    navController.navigate(transactionSummaryRouter)
                },
                onScreenSelected = { selectedScreen = it },
                onTransactionSelected = {
                    navController.navigate(transactionDetailRouter.replace("{id}", it.toString()))
                },
                onWhatsNewClick = {
                    navController.navigate(webViewRouter.replace("{url}", GITHUB_RELEASES_URL))
                },
                onBudgetItemClick = {
                    navController.navigate(budgetDetailRouter.replace("{id}", it.toString()))
                }
            )
        }
        composable(
            route = transactionDetailRouter,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { navBackStackEntry ->
            val uiModel = koinViewModel<ITransactionDetailViewModel>()

            navBackStackEntry.arguments?.getLong("id")?.let {
                TransactionsDetailScreen(id = it, uiModel = uiModel) {
                    navController.navigateUp()
                }
            }
        }
        composable(
            route = budgetDetailRouter,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val uiModel = koinViewModel<IBudgetDetailViewModel>()

            navBackStackEntry.arguments?.getInt("id")?.let {
                BudgetDetailScreen(
                    budgetId = it,
                    uiModel = uiModel,

                ) {
                    navController.navigateUp()
                }
            }
        }
        composable(
            route = analyzeRouter,
        ) {
            val uiModel = koinViewModel<IAnalyzeViewModel>()

            AnalyzeScreen(uiModel) {
                navController.navigateUp()
            }
        }
        composable(
            route = transactionSummaryRouter,
        ) {
            val uiModel = koinViewModel<ITransactionsSummaryViewModel>()

            TransactionsSummaryScreen(uiModel) {
                navController.navigateUp()
            }
        }
        composable(
            route = webViewRouter,
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