package com.csk.myjpmcandroid.data.source.network

import com.csk.myjpmcandroid.data.model.GoogleMapsApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleMapsNetworkApi {

    @GET
    suspend fun getAddressByLatNLng(@Url url: String): GoogleMapsApiResponse
}