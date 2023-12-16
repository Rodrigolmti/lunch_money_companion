package com.rodrigolmti.lunch.money.features.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigolmti.lunch.money.IMainActivityViewModel
import com.rodrigolmti.lunch.money.features.authentication.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.features.authentication.ui.IAuthenticationViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationGraph(viewModel: IMainActivityViewModel) {

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
            BottomNavigation(selectedScreen = selectedScreen) {
                selectedScreen = it
            }
        }
    }
}