package com.csk.astromap.domain

import com.csk.astromap.data.model.GoogleMapsApiResponse

interface GoogleMapsRepo {

    suspend fun getAddressByLatLng(apiKey: String, latLng: String): GoogleMapsApiResponse
}