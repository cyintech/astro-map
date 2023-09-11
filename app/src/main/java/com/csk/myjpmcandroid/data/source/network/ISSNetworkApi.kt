package com.csk.myjpmcandroid.data.source.network

import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation
import retrofit2.http.GET

/**
 * This is an interface to write all the methods with @GET annotation that takes in an endpoint url
 * to communicate with the backend server to get the result specified as a return type.
 */

interface ISSNetworkApi {

    @GET("iss-now")
    suspend fun getISSLocation(): ISSLocation

    @GET("astros")
    suspend fun getISSOnBoardAstronauts(): ISSAstros
}