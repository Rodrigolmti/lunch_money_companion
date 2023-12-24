package com.rodrigolmti.lunch.money.companion.composition.di

import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.di.adapter.HomeFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.di.adapter.TransactionFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.IAuthenticationViewModel
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
            getUserAccountOverview = {
                HomeFeatureAdapter(get()).getAssetOverview()
            },
            refreshUserData = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}

private val transactionsModule = module {
    viewModel<ITransactionsViewModel> {
        TransactionsViewModel(
            getUserTransactions = {
                TransactionFeatureAdapter(get()).getTransactions()
            },
        )
    }
}

private val settingsModule = module {
    viewModel<ISettingsViewModel> {
        SettingsViewModel(
            logoutUser = { get<ILunchRepository>().logoutUser() }
        )
    }
}

internal val featuresModule = module {
    includes(
        authenticationModule,
        homeModule,
        transactionsModule,
        settingsModule
    )
}