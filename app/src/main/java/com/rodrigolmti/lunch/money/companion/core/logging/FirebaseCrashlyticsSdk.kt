package com.rodrigolmti.lunch.money.companion.core.logging

import com.google.firebase.crashlytics.FirebaseCrashlytics

private const val UNDEFINED_USER_ID = "undefined"

/**
 * Firebase crashlytics implementation for crash reporting;
 */
class FirebaseCrashlyticsSdk(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ICrashlyticsSdk {

    override fun setCollectionEnabled(enabled: Boolean) {
        firebaseCrashlytics.setCrashlyticsCollectionEnabled(enabled)
    }

    /**
     * Data
     * @see <a href="https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=android#set-user-ids">Set user identifiers</a>
     */
    override fun setUserId(id: String) {
        firebaseCrashlytics.setUserId(
            id.ifBlank {
                // blank can't be used for searches on firebase
                UNDEFINED_USER_ID
            }
        )
    }

    /**
     * Logs (breadcrumbs)
     * @see <a href="https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=android#add-logs">Add custom log messages</a>
     */
    override fun log(message: String) {
        firebaseCrashlytics.log(message)
    }

    /**
     * Event type = Non-fatals
     * @see <a href="https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=android#log-excepts">Report non-fatal exceptions</a>
     */
    override fun logNonFatal(exception: Throwable) {
        firebaseCrashlytics.recordException(exception)
    }

    /**
     * Keys
     * @see <a href="https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=android#add-keys">Add custom keys</a>
     */
    override fun setCustomKey(key: String, value: Any) {
        when (value) {
            is String -> firebaseCrashlytics.setCustomKey(key, value)
            is Int -> firebaseCrashlytics.setCustomKey(key, value)
            is Boolean -> firebaseCrashlytics.setCustomKey(key, value)
            is Float -> firebaseCrashlytics.setCustomKey(key, value)
            is Long -> firebaseCrashlytics.setCustomKey(key, value)
            else -> {
                // unsupported type
            }
        }
    }

    override fun didCrashOnPreviousExecution(): Boolean =
        firebaseCrashlytics.didCrashOnPreviousExecution()
}