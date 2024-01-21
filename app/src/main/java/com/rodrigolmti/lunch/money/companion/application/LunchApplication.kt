package com.rodrigolmti.lunch.money.companion.application

import android.app.Application
import com.rodrigolmti.lunch.money.companion.composition.di.compositionModule
import com.rodrigolmti.lunch.money.companion.composition.di.featuresModule
import com.rodrigolmti.lunch.money.companion.composition.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext

class LunchApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@LunchApplication)
            modules(
                listOf(
                    compositionModule,
                    networkModule,
                    featuresModule,
                )
            )
        }
    }
}