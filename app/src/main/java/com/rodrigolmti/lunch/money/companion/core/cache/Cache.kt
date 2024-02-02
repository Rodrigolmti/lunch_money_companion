package com.rodrigolmti.lunch.money.companion.core.cache

interface Cache<K, V> {
    fun put(key: K, value: V, policy: CachePolicy = CachePolicy.NeverExpire)
    fun get(key: K, default: V): V
    fun get(key: K): V?
    fun clear()
}

