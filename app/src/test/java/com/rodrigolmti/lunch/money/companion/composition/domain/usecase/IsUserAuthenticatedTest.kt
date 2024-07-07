package com.rodrigolmti.lunch.money.companion.composition.domain.usecase

import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsUserAuthenticatedTest {

    private val lunchRepository: IAppRepository = mockk()
    private val sut = IsUserAuthenticated(lunchRepository)
    private val user = mockk<UserModel>(relaxed = true)

    @Test
    fun `invoke should return true when user is authenticated`() {
        every { lunchRepository.getSessionUser() } returns user

        val result = sut()

        assertTrue(result)
    }

    @Test
    fun `invoke should return false when user is un-authenticated`() {
        every { lunchRepository.getSessionUser() } returns null

        val result = sut()

        assertFalse(result)
    }
}