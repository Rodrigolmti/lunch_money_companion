package com.rodrigolmti.lunch.money.composition.di.adapter

import com.rodrigolmti.lunch.money.composition.data.repository.ILunchRepository
import com.rodrigolmti.lunch.money.composition.di.mapper.toView
import com.rodrigolmti.lunch.money.core.LunchError
import com.rodrigolmti.lunch.money.core.Outcome
import com.rodrigolmti.lunch.money.core.mapThrowable
import com.rodrigolmti.lunch.money.core.runCatching
import com.rodrigolmti.lunch.money.features.home.model.AssetOverviewView

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