package com.rodrigolmti.lunch.money.core.cache

interface ICacheManager {
    fun <K, V> createCache(
        name: String,
        policy: CachePolicy = CachePolicy.NeverExpire
    ): Cache<K, V>

    fun <K, V> getCache(name: String): Cache<K, V>?
}

class CacheManager : ICacheManager {
    private val caches = mutableMapOf<String, Cache<Any, Any>>()

    override fun <K, V> createCache(
        name: String,
        policy: CachePolicy
    ): Cache<K, V> {
        val cache = InMemoryCache<K, V>()
        caches[name] = cache as Cache<Any, Any>
        return cache
    }

    override fun <K, V> getCache(name: String): Cache<K, V>? {
        return caches[name] as? Cache<K, V>
    }
}