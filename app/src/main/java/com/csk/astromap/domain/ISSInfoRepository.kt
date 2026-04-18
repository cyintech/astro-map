package com.csk.astromap.domain

import com.csk.astromap.data.model.ISSAstros
import com.csk.astromap.data.model.ISSLocation

interface ISSInfoRepository {

    suspend fun getISSLocation(): ISSLocation

    suspend fun getISSOnBoardAstronauts(): ISSAstros
}