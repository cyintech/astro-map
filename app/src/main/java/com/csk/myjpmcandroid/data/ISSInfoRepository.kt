package com.csk.myjpmcandroid.data

import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation

interface ISSInfoRepository {

    suspend fun getISSLocation(): ISSLocation

    suspend fun getISSOnBoardAstronauts(): ISSAstros
}