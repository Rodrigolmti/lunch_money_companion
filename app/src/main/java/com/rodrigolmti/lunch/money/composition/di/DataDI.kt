package com.rodrigolmti.lunch.money.composition.di

import android.content.Context
import android.content.SharedPreferences
import com.rodrigolmti.lunch.money.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.data.repository.LunchRepository
import com.rodrigolmti.lunch.money.composition.domain.usecase.ExecuteStartupLogic
import com.rodrigolmti.lunch.money.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.composition.domain.usecase.IsUserAuthenticated
import com.rodrigolmti.lunch.money.composition.domain.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.core.SharedPreferencesDelegateFactory
import org.koin.dsl.module
import retrofit2.Retrofit

private const val SHARED_PREFERENCES = "lunch_money_preferences"

internal val serviceModule = module {
    single<LunchService> { get<Retrofit>().create(LunchService::class.java) }
}

internal val dataModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
    single { SharedPreferencesDelegateFactory(get()) }
    single<ILunchRepository> { LunchRepository(get(), get(), get(), get()) }
}

internal val domainModule = module {
    factory<IsUserAuthenticatedUseCase> { IsUserAuthenticated(get()) }
    factory<ExecuteStartupLogicUseCase> { ExecuteStartupLogic(get()) }
}