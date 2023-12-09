package com.rodrigolmti.lunch.money

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rodrigolmti.lunch.money.ui.features.start.ui.AuthenticationScreen
import com.rodrigolmti.lunch.money.ui.features.start.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.theme.LunchMoneyTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val uiModel = koinViewModel<IAuthenticationViewModel>()

            LunchMoneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthenticationScreen(uiModel)
                }
            }
        }
    }
}