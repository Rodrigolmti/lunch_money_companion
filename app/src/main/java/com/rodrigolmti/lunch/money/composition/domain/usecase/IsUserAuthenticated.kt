package com.rodrigolmti.lunch.money.composition.domain.usecase

import com.rodrigolmti.lunch.money.composition.domain.repository.ILunchRepository

internal interface IsUserAuthenticatedUseCase {
    operator fun invoke(): Boolean
}

internal class IsUserAuthenticated(
    private val lunchRepository: ILunchRepository
) : IsUserAuthenticatedUseCase {
    override operator fun invoke(): Boolean {
        val user = lunchRepository.getSessionUser()
        return user != null
    }
}