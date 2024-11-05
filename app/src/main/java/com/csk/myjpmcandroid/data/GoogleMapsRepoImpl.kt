package com.csk.myjpmcandroid.data

import com.csk.myjpmcandroid.data.model.GoogleMapsApiResponse
import com.csk.myjpmcandroid.data.source.network.GoogleMapsNetworkApi
import com.csk.myjpmcandroid.domain.GoogleMapsRepo
import javax.inject.Inject

class GoogleMapsRepoImpl @Inject constructor(private val apiService: GoogleMapsNetworkApi): GoogleMapsRepo {
    override suspend fun getAddressByLatLng(apiKey: String, latLng: String): GoogleMapsApiResponse {
        val baseUrl = "https://maps.googleapis.com/"
        val endPoint = "maps/api/geocode/json?"
        val url = baseUrl+endPoint+"latlng="+latLng+"&key="+apiKey
        return apiService.getAddressByLatNLng(url)
    }
}