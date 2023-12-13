package com.rodrigolmti.lunch.money.composition.data.usecase

import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching
import com.rodrigolmti.lunch.money.ui.features.transactions.data.model.AssetOverviewModel

interface GetAssetOverviewUseCase {
    suspend operator fun invoke(): Outcome<List<AssetOverviewModel>, LunchError>
}

internal class GetAssetOverview(
    private val lunchRepository: ILunchRepository
) : GetAssetOverviewUseCase {

    override suspend operator fun invoke(): Outcome<List<AssetOverviewModel>, LunchError> {
        return runCatching {
            val assets = lunchRepository.getAssets()

            val overview: MutableList<AssetOverviewModel> = mutableListOf()

            assets.groupBy { it.type }.forEach { (key, value) ->
                overview.add(
                    AssetOverviewModel(
                        value.sumOf { it.balance },
                        key,
                        value,
                    )
                )
            }

            overview
        }.mapThrowable {
            LunchError.EmptyDataError
        }
    }
}