package com.rodrigolmti.lunch.money.companion.composition.di

import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.BudgetFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.HomeFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.SettingsFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.TransactionFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.IBudgetViewModel
import com.rodrigolmti.lunch.money.companion.features.home.ui.HomeViewModel
import com.rodrigolmti.lunch.money.companion.features.home.ui.IHomeViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.ISettingsViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.SettingsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authenticationModule = module {
    viewModel<IAuthenticationViewModel> {
        AuthenticationViewModel(
            authenticateUser = { get<ILunchRepository>().authenticateUser(TokenDTO(it)) },
            postAuthentication = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}

private val homeModule = module {
    viewModel<IHomeViewModel> {
        HomeViewModel(
            getUserAccountOverview = { start, end ->
                HomeFeatureAdapter(get()).getAssetOverview(start, end)
            },
            refreshUserData = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}

private val transactionsModule = module {
    viewModel<ITransactionsViewModel> {
        TransactionsViewModel(
            getUserTransactions = { start, end ->
                TransactionFeatureAdapter(get()).getTransactions(start, end)
            },
        )
    }
}

private val budgetModule = module {
    viewModel<IBudgetViewModel> {
        BudgetViewModel(
            getBudget = { start, end ->
                BudgetFeatureAdapter(get()).getBudget(start, end)
            },
        )
    }
}

private val settingsModule = module {
    viewModel<ISettingsViewModel> {
        SettingsViewModel(
            logoutUserRunner = { get<ILunchRepository>().logoutUser() },
            getUserDataRunner = {
                SettingsFeatureAdapter(get()).getUserData()
            },
        )
    }
}

internal val featuresModule = module {
    includes(
        authenticationModule,
        homeModule,
        transactionsModule,
        budgetModule,
        settingsModule
    )
}