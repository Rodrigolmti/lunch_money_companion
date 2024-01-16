package com.rodrigolmti.lunch.money.companion.composition.domain.usecase

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class ExecuteStartupLogicTest {

    private val lunchRepository: ILunchRepository = mockk()
    private val sut = ExecuteStartupLogic(lunchRepository)

    @Test
    fun `invoke should call cache transaction categories`(): Unit = runBlocking {
        init()

        sut()

        coVerify(exactly = 1) { lunchRepository.cacheTransactionCategories() }
    }

    @Test
    fun `invoke should call cache assets`(): Unit = runBlocking {
        init()

        sut()

        coVerify(exactly = 1) { lunchRepository.cacheAssets() }
    }

    @Test
    fun `invoke should execute cache transaction first`() = runBlocking {
        init()

        sut()

        coVerifyOrder {
            lunchRepository.cacheTransactionCategories()
            lunchRepository.cacheAssets()
        }
    }

    @Test
    fun `invoke should return unknown error when cache transaction categories throws exception`() =
        runBlocking {
            coEvery { lunchRepository.cacheTransactionCategories() } throws Exception()

            val result = sut()

            assert(result.isFailure)
        }

    private fun init() {
        coEvery { lunchRepository.cacheTransactionCategories() } just runs
        coEvery { lunchRepository.cacheAssets() } just runs
    }
}