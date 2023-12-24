@file:OptIn(ExperimentalContracts::class, ExperimentalContracts::class,
    ExperimentalContracts::class
)

package com.rodrigolmti.lunch.money.companion.core

import java.io.Serializable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A discriminated union that encapsulates a successful outcome with a value of type [Value]
 * or a failure with an arbitrary [Error] exception.
 */
@JvmInline
value class Outcome<out Value, out Error> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any?
) : Serializable {

    // discovery

    /**
     * Returns `true` if this instance represents a successful outcome.
     * In this case [isFailure] returns `false`.
     */
    val isSuccess: Boolean get() = value !is Failure<*>

    /**
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    val isFailure: Boolean get() = value is Failure<*>

    // value & exception retrieval

    /**
     * Returns the encapsulated value if this instance represents [success][Outcome.isSuccess] or `null`
     * if it is [failure][Outcome.isFailure].
     *
     * This function is a shorthand for `getOrElse { null }` (see [getOrElse]) or
     * `fold(onSuccess = { it }, onFailure = { null })` (see [fold]).
     */
    inline fun getOrNull(): Value? =
        when {
            isFailure -> null
            else -> value as Value
        }

    /**
     * Returns the encapsulated [Error] exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     * This function is a shorthand for `fold(onSuccess = { null }, onFailure = { it })` (see [fold]).
     */
    fun errorOrNull(): Error? =
        when (value) {
            is Failure<*> -> value.error as Error
            else -> null
        }

    // companion with constructors

    /**
     * Companion object for [Outcome] class that contains its constructor functions
     * [success] and [failure].
     */
    companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */
        @Suppress("INAPPLICABLE_JVM_NAME")
        @JvmName("success")
        inline fun <Value, Error> success(value: Value): Outcome<Value, Error> =
            Outcome(value)

        /**
         * Returns an instance that encapsulates the given [Error] [error] as failure.
         */
        @Suppress("INAPPLICABLE_JVM_NAME")
        @JvmName("failure")
        inline fun <Value, Error> failure(error: Error): Outcome<Value, Error> =
            Outcome(createFailure(error))
    }

    internal class Failure<out Error>(
        @JvmField
        val error: Error
    ) : Serializable {
        override fun equals(other: Any?): Boolean = other is Failure<*> && error == other.error
        override fun hashCode(): Int = error.hashCode()
        override fun toString(): String = "Failure($error)"
    }
}

@PublishedApi
internal fun <Error> createFailure(error: Error): Any =
    Outcome.Failure(error)

/**
 * Calls the specified function [block] and returns its encapsulated outcome if invocation was successful,
 * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 */
inline fun <Value> runCatching(block: () -> Value): Outcome<Value, Throwable> {
    return try {
        Outcome.success(block())
    } catch (e: Throwable) {
        Outcome.failure(e)
    }
}

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its encapsulated outcome if invocation was successful,
 * catching any [Error] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 */
inline fun <Map, Value> Map.runCatching(block: Map.() -> Value): Outcome<Value, Throwable> {
    return try {
        Outcome.success(block())
    } catch (e: Throwable) {
        Outcome.failure(e)
    }
}


/**
 * Calls the specified function [block] and returns its encapsulated outcome if invocation was successful,
 * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 * Additionally, maps the caught [Throwable] to the specified [Error] type using the [mapError] function.
 */
inline fun <Value, Error> runCatching(
    crossinline mapError: (Throwable) -> Error,
    block: () -> Value
): Outcome<Value, Error> {
    return try {
        Outcome.success(block())
    } catch (e: Throwable) {
        Outcome.failure(mapError(e))
    }
}

// -- extensions ---

/**
 * Returns the encapsulated value if this instance represents [success][Outcome.isSuccess] or the
 * outcome of [onFailure] function for the encapsulated [Error] exception if it is [failure][Outcome.isFailure].
 *
 * Note, that this function rethrows any [Error] exception thrown by [onFailure] function.
 *
 * This function is a shorthand for `fold(onSuccess = { it }, onFailure = onFailure)` (see [fold]).
 */
inline fun <Value, Error, Map : Value> Outcome<Map, Error>.getOrElse(onFailure: (error: Error) -> Value): Value {
    contract {
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (val error = errorOrNull()) {
        null -> value as Map
        else -> onFailure(error)
    }
}

/**
 * Returns the encapsulated value if this instance represents [success][Outcome.isSuccess] or the
 * [defaultValue] if it is [failure][Outcome.isFailure].
 *
 * This function is a shorthand for `getOrElse { defaultValue }` (see [getOrElse]).
 */
inline fun <Value, Error, Map : Value> Outcome<Map, Error>.getOrDefault(defaultValue: Value): Value {
    if (isFailure) return defaultValue
    return value as Map
}

/**
 * Returns the outcome of [onSuccess] for the encapsulated value if this instance represents [success][Outcome.isSuccess]
 * or the outcome of [onFailure] function for the encapsulated [Error] exception if it is [failure][Outcome.isFailure].
 *
 * Note, that this function rethrows any [Error] exception thrown by [onSuccess] or by [onFailure] function.
 */
inline fun <Value, Error, Map> Outcome<Map, Error>.fold(
    onSuccess: (value: Map) -> Value,
    onFailure: (error: Error) -> Value
): Value {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (val error = errorOrNull()) {
        null -> onSuccess(value as Map)
        else -> onFailure(error)
    }
}

// transformation

/**
 * Returns the encapsulated outcome of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Outcome.isSuccess] or the
 * original encapsulated [Error] exception if it is [failure][Outcome.isFailure].
 *
 * Note, that this function rethrows any [Error] exception thrown by [transform] function.
 * See [mapCatching] for an alternative that encapsulates exceptions.
 */
inline fun <Value, Error, Map> Outcome<Map, Error>.map(transform: (value: Map) -> Value): Outcome<Value, Error> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when {
        isSuccess -> Outcome.success(transform(value as Map))
        else -> Outcome(value)
    }
}

/**
 * Returns a new `Outcome` instance with the outcome of the [throwableTransform] function applied to any encapsulated [Throwable]
 * exception if this instance represents [failure][Outcome.isFailure]. If it is [success][Outcome.isSuccess],
 * the original encapsulated value is kept unchanged.
 *
 * This function catches any [Throwable] exception thrown by [throwableTransform] function and encapsulates it as a failure.
 */
inline fun <Value, Error> Outcome<Value, Throwable>.mapThrowable(
    crossinline throwableTransform: (Throwable) -> Error
): Outcome<Value, Error> {
    return when (val exception = errorOrNull()) {
        null -> Outcome.success(value as Value)
        else -> Outcome.failure(throwableTransform(exception))
    }
}

/**
 * Returns the encapsulated outcome of the given [transform] function applied to the encapsulated [Error] exception
 * if this instance represents [failure][Outcome.isFailure] or the
 * original encapsulated value if it is [success][Outcome.isSuccess].
 *
 * Note, that this function rethrows any [Error] exception thrown by [transform] function.
 * See [recoverCatching] for an alternative that encapsulates exceptions.
 */
inline fun <Value, Error, Map : Value> Outcome<Map, Error>.recover(transform: (error: Error) -> Value): Outcome<Value, Error> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (val exception = errorOrNull()) {
        null -> this
        else -> Outcome.success(transform(exception))
    }
}

/**
 * Returns a new `Outcome` instance with the outcome of the [transform] function applied to the encapsulated [Error] exception
 * if this instance represents [failure][Outcome.isFailure].
 *
 * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
 * See [recover] for an alternative that rethrows exceptions.
 */
inline fun <Value, Error, Map> Outcome<Value, Error>.recoverCatching(
    crossinline transform: (exception: Error) -> Map
): Outcome<Map, Throwable> {
    return when (val error = errorOrNull()) {
        null -> Outcome.success(value as Map)
        else -> runCatching { transform(error) }
    }
}

// "peek" onto value/exception and pipe

/**
 * Performs the given [action] on the encapsulated [Error] exception if this instance represents [failure][Outcome.isFailure].
 * Returns the original `Outcome` unchanged.
 */
inline fun <Value, Error> Outcome<Value, Error>.onFailure(action: (error: Error) -> Unit): Outcome<Value, Error> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    errorOrNull()?.let { action(it) }
    return this
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [success][Outcome.isSuccess].
 * Returns the original `Outcome` unchanged.
 */
inline fun <Value, Error> Outcome<Value, Error>.onSuccess(action: (value: Value) -> Unit): Outcome<Value, Error> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (isSuccess) action(value as Value)
    return this
}

inline fun <Value, Error> Outcome<Value, Error>.onFinally(action: () -> Unit): Outcome<Value, Error> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    action()
    return this
}

// -------------------