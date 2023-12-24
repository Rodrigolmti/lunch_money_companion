package com.rodrigolmti.lunch.money.companion.application.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.rodrigolmti.lunch.money.companion.features.navigation.NavigationGraph
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.theme.LunchMoneyCompanionTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = koinViewModel<IMainActivityViewModel>()
            val viewState by viewModel.viewState.collectAsStateWithLifecycle()

            val navController = rememberNavController()

            LunchMoneyCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    when (viewState) {
                        MainActivityUiState.Loading -> {
                            Center {
                                LunchLoading()
                            }
                        }

                        MainActivityUiState.Finished -> {
                            NavigationGraph(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}