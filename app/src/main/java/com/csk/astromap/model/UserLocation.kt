package com.csk.astromap.model

import com.csk.astromap.data.model.ISSLocation

data class UserLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class UserAndISSLocation(
    var userLocation: UserLocation? = null,
    var issLocation: ISSLocation? = null,
)

