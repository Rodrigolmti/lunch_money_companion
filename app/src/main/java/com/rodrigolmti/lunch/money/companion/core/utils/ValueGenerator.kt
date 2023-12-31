package com.rodrigolmti.lunch.money.companion.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.random.Random

internal class ValueGenerator private constructor() {

    companion object {
        val instance: ValueGenerator by lazy { ValueGenerator() }

        inline fun <reified T : Any> gen(min: T? = null, max: T? = null): T {
            return instance.internalGenerateValue(min, max)
        }

        internal fun genId(): String {
            return instance.generateStringIdInternal()
        }

        internal fun genDate(format: String = "dd/MM/yyyy HH:mm:ss"): String {
            return instance.generateDateStringInternal(format)
        }

    }

    private inline fun <reified T : Any> internalGenerateValue(min: T? = null, max: T? = null): T {
        return when (T::class) {
            String::class -> generateString() as T
            Int::class -> generateInt(min as? Int, max as? Int) as T
            Boolean::class -> generateBoolean() as T
            Double::class -> generateDouble(min as? Double, max as? Double) as T
            Float::class -> generateFloat(min as? Float, max as? Float) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private fun generateString(): String {
        val source = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..10).map { source.random() }.joinToString("")
    }

    private fun generateInt(min: Int? = null, max: Int? = null): Int {
        return Random.nextInt(min ?: -100, (max ?: 1000) + 1)
    }

    private fun generateBoolean(): Boolean {
        return Random.nextBoolean()
    }

    private fun generateDouble(min: Double? = null, max: Double? = null): Double {
        return Random.nextDouble(min ?: -100.0, max ?: 1000.0)
    }

    private fun generateFloat(min: Float? = null, max: Float? = null): Float {
        return Random.nextFloat() * ((max ?: 1000f) - (min ?: -100f)) + (min ?: -100f)
    }

    private fun generateStringIdInternal(): String {
        return UUID.randomUUID().toString()
    }

    private fun generateDateStringInternal(format: String): String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(Date())
    }
}