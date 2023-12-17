package com.rodrigolmti.lunch.money.features.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodrigolmti.lunch.money.application.main.IMainActivityViewModel
import com.rodrigolmti.lunch.money.features.authentication.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.features.authentication.ui.IAuthenticationViewModel
import org.koin.androidx.compose.koinViewModel

internal const val authenticationRoute = "/authentication"
internal const val dashboardRoute = "/dashboard"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationGraph(
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

            AuthenticationScreen(uiModel) {
                navController.navigate(dashboardRoute)
            }
        }
        composable(dashboardRoute) {
            BottomNavigation(selectedScreen = selectedScreen,
                onLogoutRequested = {
                    viewModel.logout()
                }) {
                selectedScreen = it
            }
        }
    }
}