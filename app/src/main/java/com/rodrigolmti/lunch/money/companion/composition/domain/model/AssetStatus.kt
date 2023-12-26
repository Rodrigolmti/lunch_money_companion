package com.rodrigolmti.lunch.money.companion.composition.domain.model

internal enum class AssetStatus {
    ACTIVE,
    INACTIVE,
    RELINK,
    SYNCING,
    ERROR,
    NOT_FOUND,
    NOT_SUPPORTED,
    UNKNOWN
}