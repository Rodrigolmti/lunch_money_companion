package com.rodrigolmti.lunch.money.companion.core.network

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor(private val getToken: () -> String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        getToken() ?: return chain.proceed(chain.request())

        val original = chain.request()

        val hasAnnotation = original.tag(Invocation::class.java)
            ?.method()
            ?.annotations
            ?.any { it is Authenticated } == true

        return if (hasAnnotation ) {
            val newRequest = original.newBuilder()
                .addHeader("Authorization", getToken()!!)
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(original)
        }
    }
}