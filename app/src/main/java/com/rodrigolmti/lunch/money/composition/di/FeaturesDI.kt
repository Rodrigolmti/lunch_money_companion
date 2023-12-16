package com.rodrigolmti.lunch.money.composition.di

import com.rodrigolmti.lunch.money.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.di.adapter.HomeFeatureAdapter
import com.rodrigolmti.lunch.money.composition.di.adapter.TransactionFeatureAdapter
import com.rodrigolmti.lunch.money.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.features.authentication.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.features.home.ui.HomeViewModel
import com.rodrigolmti.lunch.money.features.home.ui.IHomeViewModel
import com.rodrigolmti.lunch.money.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.features.transactions.ui.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authenticationModule = module {
    viewModel<IAuthenticationViewModel> {
        AuthenticationViewModel(
            authenticateUser = { get<ILunchRepository>().authenticateUser(TokenDTO(it)) },
            storeUser = { get<ILunchRepository>().storeUser(it) },
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

internal val featuresModule = module {
    includes(
        authenticationModule,
        homeModule,
        transactionsModule,
    )
}