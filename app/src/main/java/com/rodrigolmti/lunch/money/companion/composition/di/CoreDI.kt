package com.rodrigolmti.lunch.money.companion.composition.di

import com.rodrigolmti.lunch.money.companion.core.DispatchersProvider
import com.rodrigolmti.lunch.money.companion.core.IDispatchersProvider
import org.koin.dsl.module

internal val coreModule = module {
    single<IDispatchersProvider> { DispatchersProvider() }
}