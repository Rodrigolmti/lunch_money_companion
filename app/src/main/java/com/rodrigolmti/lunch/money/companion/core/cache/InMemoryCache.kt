package com.rodrigolmti.lunch.money.companion.core.cache

import java.time.Duration
import java.time.Instant

class InMemoryCache<K, V> : Cache<K, V> {
    private val cache = HashMap<K, CacheEntry<V>>()

    override fun put(key: K, value: V, policy: CachePolicy) {
        val time = Instant.now()
        cache[key] = CacheEntry(value, policy, time, time)
    }

    override fun get(key: K, default: V): V {
        val entry = cache[key] ?: return default
        if (isEntryExpired(entry)) {
            cache.remove(key)
            return default
        }
        updateEntryUsage(entry)
        return entry.value
    }

    override fun get(key: K): V? {
        val entry = cache[key] ?: return null
        if (isEntryExpired(entry)) {
            cache.remove(key)
            return null
        }
        updateEntryUsage(entry)
        return entry.value
    }

    override fun clear() {
        cache.clear()
    }

    private fun isEntryExpired(entry: CacheEntry<V>): Boolean {
        return when (entry.policy) {
            is CachePolicy.NeverExpire -> false
            is CachePolicy.ExpireAfterAccess -> Duration.between(
                entry.lastAccessTime,
                Instant.now()
            ) > entry.policy.duration

            is CachePolicy.ExpireAfterWrite -> Duration.between(
                entry.creationTime,
                Instant.now()
            ) > entry.policy.duration

            is CachePolicy.ExpireAfterCalls -> entry.numberOfCalls >= entry.policy.maxCalls
        }
    }

    private fun updateEntryUsage(entry: CacheEntry<V>) {
        entry.lastAccessTime = Instant.now()
        entry.numberOfCalls++
    }

    private data class CacheEntry<V>(
        val value: V,
        val policy: CachePolicy,
        val creationTime: Instant,
        var lastAccessTime: Instant,
        var numberOfCalls: Int = 0
    )
}
