package com.csk.astromap.data.source.network

import com.csk.astromap.data.model.GoogleMapsApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleMapsNetworkApi {

    @GET
    suspend fun getAddressByLatNLng(@Url url: String): GoogleMapsApiResponse
}