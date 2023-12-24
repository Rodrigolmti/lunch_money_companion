package com.rodrigolmti.lunch.money.companion.composition.di

import android.content.Context
import android.content.SharedPreferences
import com.rodrigolmti.lunch.money.companion.composition.data.network.LunchService
import com.rodrigolmti.lunch.money.companion.composition.data.repository.LunchRepository
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.ExecuteStartupLogic
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.IsUserAuthenticated
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.IsUserAuthenticatedUseCase
import com.rodrigolmti.lunch.money.companion.core.SharedPreferencesDelegateFactory
import com.rodrigolmti.lunch.money.companion.core.cache.CacheManager
import com.rodrigolmti.lunch.money.companion.core.cache.ICacheManager
import org.koin.dsl.module
import retrofit2.Retrofit

private const val SHARED_PREFERENCES = "lunch_money_preferences"

private val serviceModule = module {
    single<LunchService> { get<Retrofit>().create(LunchService::class.java) }
}

private val dataModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
    single<ICacheManager> { CacheManager() }
    single { SharedPreferencesDelegateFactory(get()) }
    single<ILunchRepository> { LunchRepository(get(), get(), get(), get(), get()) }
}

private val domainModule = module {
    factory<IsUserAuthenticatedUseCase> { IsUserAuthenticated(get()) }
    factory<ExecuteStartupLogicUseCase> { ExecuteStartupLogic(get()) }
}

internal val compositionModule = module {
    includes(
        serviceModule,
        dataModule,
        domainModule,
    )
}