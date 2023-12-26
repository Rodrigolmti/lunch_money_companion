package com.rodrigolmti.lunch.money.companion.composition.data.mapper

import com.rodrigolmti.lunch.money.companion.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel

internal fun UserResponse.toModel() = UserModel(
    userName = userName,
    email = email,
    id = id,
    accountId = accountId,
    budgetName = budgetName,
    apiKeyLabel = apiKeyLabel,
)