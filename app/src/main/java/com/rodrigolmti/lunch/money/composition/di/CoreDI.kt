package com.rodrigolmti.lunch.money.composition.di

import com.rodrigolmti.lunch.money.core.DispatchersProvider
import com.rodrigolmti.lunch.money.core.IDispatchersProvider
import org.koin.dsl.module

internal val coreModule = module {
    single<IDispatchersProvider> { DispatchersProvider() }
}