package com.rodrigolmti.lunch.money.composition.di

import com.rodrigolmti.lunch.money.IMainActivityViewModel
import com.rodrigolmti.lunch.money.MainActivityViewModel
import com.rodrigolmti.lunch.money.composition.data.model.model.Token
import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.data.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.ui.features.start.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.ui.features.start.ui.IAuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val getStartedModule = module {
    viewModel<IMainActivityViewModel> {
        MainActivityViewModel(
            isUserAuthenticated = { get<IsUserAuthenticatedUseCase>().invoke() }
        )
    }
    viewModel<IAuthenticationViewModel> {
        AuthenticationViewModel(
            authenticateUser = { get<ILunchRepository>().authenticateUser(Token(it)) },
            storeUser = { get<ILunchRepository>().storeUser(it) },
        )
    }
}

internal val featuresModule = module {
    includes(getStartedModule)
}