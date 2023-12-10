package com.rodrigolmti.lunch.money.composition.data.usecase

import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository

internal interface IsUserAuthenticatedUseCase {
    operator fun invoke(): Boolean
}

internal class IsUserAuthenticated(
    private val lunchRepository: ILunchRepository
) : IsUserAuthenticatedUseCase {
    override operator fun invoke(): Boolean {
        val user = lunchRepository.getUser()
        return user != null
    }
}