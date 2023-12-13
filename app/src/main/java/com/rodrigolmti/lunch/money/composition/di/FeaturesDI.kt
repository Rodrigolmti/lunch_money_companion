package com.rodrigolmti.lunch.money.composition.di

import com.rodrigolmti.lunch.money.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.data.usecase.GetAssetOverviewUseCase
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.home.HomeViewModel
import com.rodrigolmti.lunch.money.ui.features.home.IHomeViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.TransactionsViewModel
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
            getUserAccountOverview = { get<GetAssetOverviewUseCase>().invoke() },
        )
    }
}

private val transactionsModule = module {
    viewModel<ITransactionsViewModel> {
        TransactionsViewModel(
            getUserTransactions = { get<ILunchRepository>().getTransactions() },
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