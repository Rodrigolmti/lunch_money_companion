package com.rodrigolmti.lunch.money.companion.composition.domain.usecase

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository

internal interface IsUserAuthenticatedUseCase {
    operator fun invoke(): Boolean
}

internal class IsUserAuthenticated(
    private val lunchRepository: IAppRepository
) : IsUserAuthenticatedUseCase {
    override operator fun invoke(): Boolean {
        val user = lunchRepository.getSessionUser()
        return user != null
    }
}