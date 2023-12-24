package com.rodrigolmti.lunch.money.companion.core

import kotlinx.coroutines.Dispatchers

interface IDispatchersProvider {
    fun io() = Dispatchers.IO

    fun main() = Dispatchers.Main

    fun default() = Dispatchers.Default

    fun unconfined() = Dispatchers.Unconfined
}

class DispatchersProvider : IDispatchersProvider