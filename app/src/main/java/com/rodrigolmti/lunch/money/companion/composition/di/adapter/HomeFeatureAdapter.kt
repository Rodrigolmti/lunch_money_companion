package com.rodrigolmti.lunch.money.companion.composition.di.adapter

import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.features.home.model.AssetOverviewView
import com.rodrigolmti.lunch.money.companion.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.companion.core.mapThrowable
import com.rodrigolmti.lunch.money.companion.core.runCatching

internal class HomeFeatureAdapter(private val lunchRepository: ILunchRepository) {

    fun getAssetOverview(): Outcome<List<AssetOverviewView>, LunchError> {
        return runCatching {
            val assets = lunchRepository.getAssets()

            val overview: MutableList<AssetOverviewView> = mutableListOf()

            assets.groupBy { it.type }.forEach { (key, value) ->
                overview.add(
                    AssetOverviewView(
                        value.sumOf { it.balance },
                        key.toView(),
                        value.map { asset -> asset.toView() },
                    )
                )
            }

            overview
        }.mapThrowable {
            LunchError.EmptyDataError
        }
    }
}