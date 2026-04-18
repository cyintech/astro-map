package com.csk.astromap.data

import com.csk.astromap.data.model.GoogleMapsApiResponse
import com.csk.astromap.data.source.network.GoogleMapsNetworkApi
import com.csk.astromap.domain.GoogleMapsRepo
import javax.inject.Inject

class GoogleMapsRepoImpl @Inject constructor(private val apiService: GoogleMapsNetworkApi): GoogleMapsRepo {
    override suspend fun getAddressByLatLng(apiKey: String, latLng: String): GoogleMapsApiResponse {
        val baseUrl = "https://maps.googleapis.com/"
        val endPoint = "maps/api/geocode/json?"
        val url = baseUrl+endPoint+"latlng="+latLng+"&key="+apiKey
        return apiService.getAddressByLatNLng(url)
    }
}