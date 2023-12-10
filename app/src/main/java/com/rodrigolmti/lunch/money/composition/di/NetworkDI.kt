package com.rodrigolmti.lunch.money.composition.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rodrigolmti.lunch.money.composition.data.network.LunchService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit

private const val SERVER_URL = "https://dev.lunchmoney.app/"

internal val networkModule = module {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    val json = Json {
        ignoreUnknownKeys = true
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    single<Json> { json }
    single<OkHttpClient> { okHttpClient }
    single<Interceptor> { loggingInterceptor }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
    }
}

internal val serviceModule = module {
    single<LunchService>{ get<Retrofit>().create(LunchService::class.java) }
}