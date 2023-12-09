package com.rodrigolmti.lunch.money.composition.di

import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.data.repository.LunchRepository
import org.koin.dsl.module

internal val dataModule = module {
    factory<ILunchRepository>{ LunchRepository(get(), get()) }
}