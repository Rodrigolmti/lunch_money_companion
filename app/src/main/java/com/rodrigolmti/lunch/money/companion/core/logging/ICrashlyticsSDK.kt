package com.rodrigolmti.lunch.money.companion.core.logging

/**
 * Interface to abstract the Crashlytics SDK
 * If you want to report fatal / non fatal or set custom keys, you should use this interface
 * to avoid direct dependency on the SDK
 */
interface ICrashlyticsSdk {
    fun setCollectionEnabled(enabled: Boolean)

    fun setUserId(id: String)

    fun log(message: String)

    fun logNonFatal(exception: Throwable)

    fun setCustomKey(key: String, value: Any)

    fun didCrashOnPreviousExecution(): Boolean
}
