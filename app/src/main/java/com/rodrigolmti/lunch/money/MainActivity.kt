package com.rodrigolmti.lunch.money

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.rodrigolmti.lunch.money.uikit.components.Center
import com.rodrigolmti.lunch.money.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.features.navigation.NavigationGraph
import com.rodrigolmti.lunch.money.uikit.theme.LunchMoneyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

abstract class IMainActivityViewModel : ViewModel() {
    abstract val viewState: StateFlow<MainActivityUiState>

    abstract val isUserAuthenticated: () -> Boolean
}

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()
    data object Finished : MainActivityUiState()
}

class MainActivityViewModel(
    override val isUserAuthenticated: () -> Boolean,
    private val executeStartupLogic: suspend () -> Unit
) : IMainActivityViewModel() {

    private val _viewState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    override val viewState: StateFlow<MainActivityUiState> = _viewState

    init {
        viewModelScope.launch {
            executeStartupLogic()
            _viewState.value = MainActivityUiState.Finished
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel = koinViewModel<IMainActivityViewModel>()
            val viewState by viewModel.viewState.collectAsStateWithLifecycle()

            LunchMoneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (viewState) {
                        MainActivityUiState.Loading -> {
                            Center {
                                LunchLoading()
                            }
                        }

                        MainActivityUiState.Finished -> {
                            NavigationGraph(viewModel)
                        }
                    }
                }
            }
        }
    }
}
