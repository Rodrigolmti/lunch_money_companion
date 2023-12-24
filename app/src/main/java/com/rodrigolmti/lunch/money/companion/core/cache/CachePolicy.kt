package com.rodrigolmti.lunch.money.companion.core.cache

import java.time.Duration

sealed class CachePolicy {
    data object NeverExpire : CachePolicy()
    data class ExpireAfterAccess(val duration: Duration) : CachePolicy()
    data class ExpireAfterWrite(val duration: Duration) : CachePolicy()
    data class ExpireAfterCalls(val maxCalls: Int) : CachePolicy()
}