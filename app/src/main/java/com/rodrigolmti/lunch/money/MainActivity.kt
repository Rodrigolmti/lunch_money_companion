package com.rodrigolmti.lunch.money

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigolmti.lunch.money.ui.features.start.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.ui.features.start.ui.IAuthenticationViewModel
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

            AuthenticationScreen(uiModel)
        }
        composable(dashboardRoute) {
            Scaffold {
                Text("lala")
            }
        }
    }
}