package com.csk.myjpmcandroid.domain

import com.csk.myjpmcandroid.data.model.GoogleMapsApiResponse

interface GoogleMapsRepo {

    suspend fun getAddressByLatLng(apiKey: String, latLng: String): GoogleMapsApiResponse
}