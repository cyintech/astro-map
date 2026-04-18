package com.csk.astromap.data

import com.csk.astromap.domain.ISSInfoRepository
import com.csk.astromap.data.model.ISSAstros
import com.csk.astromap.data.model.ISSLocation
import com.csk.astromap.data.source.network.ISSNetworkApi

/**
 * This is the implementation to the ISSInfoRepository interface that takes in the ISSNetworkApi instance and calls the methods to fetch the data.
 */

class ISSInfoRepositoryImpl(private val apiService: ISSNetworkApi): ISSInfoRepository {

    override suspend fun getISSLocation(): ISSLocation = apiService.getISSLocation()

    override suspend fun getISSOnBoardAstronauts(): ISSAstros = apiService.getISSOnBoardAstronauts()
}