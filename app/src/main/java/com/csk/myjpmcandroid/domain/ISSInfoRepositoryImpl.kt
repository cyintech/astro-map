package com.csk.myjpmcandroid.domain

import com.csk.myjpmcandroid.data.ISSInfoRepository
import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation
import com.csk.myjpmcandroid.data.source.network.ISSNetworkApi

/**
 * This is the implementation to the ISSInfoRepository interface that takes in the ISSNetworkApi instance and calls the methods to fetch the data.
 */

class ISSInfoRepositoryImpl(private val apiService: ISSNetworkApi): ISSInfoRepository {

    override suspend fun getISSLocation(): ISSLocation = apiService.getISSLocation()

    override suspend fun getISSOnBoardAstronauts(): ISSAstros = apiService.getISSOnBoardAstronauts()
}