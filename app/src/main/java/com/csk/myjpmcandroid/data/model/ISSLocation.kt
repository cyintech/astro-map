package com.csk.myjpmcandroid.data.model

import com.google.gson.annotations.SerializedName

/**
 * Below are the data classes to convert the json response into kotlin objects from the endpoints
 * that return the information about the ISS location.
 */

data class ISSLocation(
    @SerializedName("iss_position")
    val issPosition: ISSPosition,
    val message: String,
    val timestamp: Int
)

data class ISSPosition(
    val latitude: String,
    val longitude: String
)