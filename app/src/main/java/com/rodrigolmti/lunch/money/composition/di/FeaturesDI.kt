package com.rodrigolmti.lunch.money.composition.di

import com.rodrigolmti.lunch.money.IMainActivityViewModel
import com.rodrigolmti.lunch.money.MainActivityViewModel
import com.rodrigolmti.lunch.money.composition.data.model.model.Token
import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.data.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.ui.features.transactions.ui.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val appModule = module {
    viewModel<IMainActivityViewModel> {
        MainActivityViewModel(
            isUserAuthenticated = { get<IsUserAuthenticatedUseCase>().invoke() }
        )
    }
}

private val authenticationModule = module {
    viewModel<IAuthenticationViewModel> {
        AuthenticationViewModel(
            authenticateUser = { get<ILunchRepository>().authenticateUser(Token(it)) },
            storeUser = { get<ILunchRepository>().storeUser(it) },
        )
    }
}

private val transactionsModule = module {
    viewModel<ITransactionsViewModel> {
        TransactionsViewModel(
            getTransactions = { get<ILunchRepository>().getTransactions() },
        )
    }
}

internal val featuresModule = module {
    includes(
        appModule,
        authenticationModule,
        transactionsModule
    )
}