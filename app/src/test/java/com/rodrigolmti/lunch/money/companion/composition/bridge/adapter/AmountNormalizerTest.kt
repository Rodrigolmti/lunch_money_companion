package com.rodrigolmti.lunch.money.companion.composition.bridge.adapter

import org.junit.Assert.assertEquals
import kotlin.test.Test

class AmountNormalizerTest {

    @Test
    fun `normalizeAmount should return same amount for positive amount`() {
        val result = AmountNormalizer.normalizeAmount(100f)
        assertEquals(100f, result, 0.0f)
    }

    @Test
    fun `normalizeAmount should return positive amount for negative amount`() {
        val result = AmountNormalizer.normalizeAmount(-100f)
        assertEquals(100f, result, 0.0f)
    }

    @Test
    fun `normalizeAmount should return zero for zero amount`() {
        val result = AmountNormalizer.normalizeAmount(0f)
        assertEquals(0f, result, 0.0f)
    }

    @Test
    fun `fixAmountBasedOnSymbol should return positive amount for income and positive amount`() {
        val result = AmountNormalizer.fixAmountBasedOnSymbol(true, 100f)
        assertEquals(100f, result, 0.0f)
    }

    @Test
    fun `fixAmountBasedOnSymbol should return positive amount for income and negative amount`() {
        val result = AmountNormalizer.fixAmountBasedOnSymbol(true, -100f)
        assertEquals(100f, result, 0.0f)
    }

    @Test
    fun `fixAmountBasedOnSymbol should return negative amount for expense and positive amount`() {
        val result = AmountNormalizer.fixAmountBasedOnSymbol(false, 100f)
        assertEquals(-100f, result, 0.0f)
    }

    @Test
    fun `fixAmountBasedOnSymbol should return negative amount for expense and negative amount`() {
        val result = AmountNormalizer.fixAmountBasedOnSymbol(false, -100f)
        assertEquals(-100f, result, 0.0f)
    }

    @Test
    fun `fixAmountBasedOnSymbol should return zero for zero amount and income`() {
        val result = AmountNormalizer.fixAmountBasedOnSymbol(true, 0f)
        assertEquals(0f, result, 0.0f)
    }

    @Test
    fun `fixAmountBasedOnSymbol should return zero for zero amount and expense`() {
        val result = AmountNormalizer.fixAmountBasedOnSymbol(false, 0f)
        assertEquals(0f, result, 0.0f)
    }
}