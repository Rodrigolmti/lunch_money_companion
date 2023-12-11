package com.rodrigolmti.lunch.money

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.rodrigolmti.lunch.money.ui.navigation.NavigationGraph
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

        window.statusBarColor = ContextCompat.getColor(this, R.color.midnight_slate)

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
