package com.rodrigolmti.lunch.money.application

import android.app.Application
import com.rodrigolmti.lunch.money.application.main.IMainActivityViewModel
import com.rodrigolmti.lunch.money.application.main.MainActivityViewModel
import com.rodrigolmti.lunch.money.composition.di.compositionModule
import com.rodrigolmti.lunch.money.composition.di.coreModule
import com.rodrigolmti.lunch.money.composition.di.featuresModule
import com.rodrigolmti.lunch.money.composition.di.networkModule
import com.rodrigolmti.lunch.money.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.composition.domain.usecase.IsUserAuthenticatedUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

class LunchApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@LunchApplication)
            modules(
                listOf(
                    coreModule,
                    appModule,
                    compositionModule,
                    networkModule,
                    featuresModule,
                )
            )
        }
    }
}

private val appModule = module {
    viewModel<IMainActivityViewModel> {
        MainActivityViewModel(
            isUserAuthenticated = { get<IsUserAuthenticatedUseCase>().invoke() },
            executeStartupLogic = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}