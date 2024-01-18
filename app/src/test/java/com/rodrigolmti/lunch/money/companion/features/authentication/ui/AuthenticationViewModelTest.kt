package com.rodrigolmti.lunch.money.companion.features.authentication.ui

import com.rodrigolmti.lunch.money.companion.MainDispatcherRule
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: IAuthenticationViewModel

    @Before
    fun setup() {
        sut = AuthenticationViewModel(
            authenticateUser = { Outcome.success(Unit) },
            postAuthentication =  {},
        )
    }

    @Test
    fun `on get started click, should set success state when success`() = runTest {
        sut = AuthenticationViewModel(
            authenticateUser = { Outcome.success(Unit) },
            postAuthentication =  {},
        )
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.viewState.collect()
        }

        val token = "token"

        sut.onGetStartedClicked(token)

        assertEquals(
            AuthenticationUiState.Success,
            sut.viewState.value
        )

        collectJob.cancel()
    }

    @Test
    fun `on get started click, should set error state when error`() = runTest {
        sut = AuthenticationViewModel(
            authenticateUser = { Outcome.failure(LunchError.UnknownError) },
            postAuthentication =  {},
        )
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.viewState.collect()
        }

        val token = "token"

        sut.onGetStartedClicked(token)

        assertEquals(
            AuthenticationUiState.Error,
            sut.viewState.value
        )

        collectJob.cancel()
    }
}