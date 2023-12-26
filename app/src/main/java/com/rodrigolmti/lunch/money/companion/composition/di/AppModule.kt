package com.rodrigolmti.lunch.money.companion.composition.di

import com.rodrigolmti.lunch.money.companion.application.main.IMainActivityViewModel
import com.rodrigolmti.lunch.money.companion.application.main.MainActivityViewModel
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.companion.core.DispatchersProvider
import com.rodrigolmti.lunch.money.companion.core.IDispatchersProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    single<IDispatchersProvider> { DispatchersProvider() }
    viewModel<IMainActivityViewModel> {
        MainActivityViewModel(
            isUserAuthenticated = { get<IsUserAuthenticatedUseCase>().invoke() },
            executeStartupLogic = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}