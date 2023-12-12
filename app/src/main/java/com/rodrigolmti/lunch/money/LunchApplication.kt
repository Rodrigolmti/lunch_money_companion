package com.rodrigolmti.lunch.money

import android.app.Application
import com.rodrigolmti.lunch.money.composition.data.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.composition.data.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.composition.di.dataModule
import com.rodrigolmti.lunch.money.composition.di.domainModule
import com.rodrigolmti.lunch.money.composition.di.featuresModule
import com.rodrigolmti.lunch.money.composition.di.networkModule
import com.rodrigolmti.lunch.money.composition.di.serviceModule
import com.rodrigolmti.lunch.money.core.DispatchersProvider
import com.rodrigolmti.lunch.money.core.IDispatchersProvider
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
                    domainModule,
                    dataModule,
                    appModule,
                    serviceModule,
                    networkModule,
                    featuresModule,
                )
            )
        }
    }
}

private val appModule = module {
    single<IDispatchersProvider> { DispatchersProvider() }
    viewModel<IMainActivityViewModel> {
        MainActivityViewModel(
            isUserAuthenticated = { get<IsUserAuthenticatedUseCase>().invoke() },
            executeStartupLogic = { get<ExecuteStartupLogicUseCase>().invoke() }
        )
    }
}