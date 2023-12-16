package com.rodrigolmti.lunch.money.composition.data.mapper

import com.rodrigolmti.lunch.money.composition.data.model.response.UserResponse
import com.rodrigolmti.lunch.money.composition.domain.model.UserModel

fun UserResponse.toModel() = UserModel(
    userName = userName,
    email = email,
    id = id,
    accountId = accountId,
    budgetName = budgetName,
    apiKeyLabel = apiKeyLabel,
)

fun UserModel.toResponse() = UserResponse(
    userName = userName,
    email = email,
    id = id,
    accountId = accountId,
    budgetName = budgetName,
    apiKeyLabel = apiKeyLabel,
)