package com.rodrigolmti.lunch.money.companion.composition.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rodrigolmti.lunch.money.companion.BuildConfig
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import com.rodrigolmti.lunch.money.companion.core.ConnectionChecker
import com.rodrigolmti.lunch.money.companion.core.SERVER_URL
import com.rodrigolmti.lunch.money.companion.core.network.AuthInterceptor
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

internal val networkModule = module {
    single {
        ConnectionChecker(get())
    }
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor {
                get<IAppRepository>().getSessionToken()?.format()
            })
            .addInterceptor(get<Interceptor>())
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json".toMediaType()),
            )
            .build()
    }
}