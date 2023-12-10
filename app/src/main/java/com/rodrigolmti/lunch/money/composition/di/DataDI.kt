package com.rodrigolmti.lunch.money.composition.di

import android.content.Context
import android.content.SharedPreferences
import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.data.repository.LunchRepository
import com.rodrigolmti.lunch.money.composition.data.usecase.IsUserAuthenticated
import com.rodrigolmti.lunch.money.composition.data.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.core.SharedPreferencesDelegateFactory
import org.koin.dsl.module

private const val SHARED_PREFERENCES = "lunch_money_preferences"

internal val domainModule = module {
    factory<IsUserAuthenticatedUseCase> { IsUserAuthenticated(get()) }
}

internal val dataModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
    single { SharedPreferencesDelegateFactory(get()) }
    factory<ILunchRepository>{ LunchRepository(get(), get(), get(), get()) }
}