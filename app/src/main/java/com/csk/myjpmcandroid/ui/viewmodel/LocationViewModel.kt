package com.csk.myjpmcandroid.ui.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import com.csk.myjpmcandroid.model.UserAndISSLocation
import com.csk.myjpmcandroid.util.DistanceCalculatorInMiles
import kotlin.math.roundToInt

/**
 * This viewModel is just created for simple use case of calculating the distance in miles from latitude and longitude
 * Keeping the logic here for simplicity purpose and due to the time constraints
 */

class LocationViewModel: ViewModel() {

    private var _distance = mutableDoubleStateOf(0.0)
    val distance = _distance

    fun calculateDistance(userAndISSLocation: UserAndISSLocation) {

        val lat1 = userAndISSLocation.userLocation?.latitude ?: 0.0
        val long1 = userAndISSLocation.userLocation?.longitude ?: 0.0
        val lat2 = userAndISSLocation.issLocation?.issPosition?.latitude?.toDouble() ?: 0.0
        val long2 = userAndISSLocation.issLocation?.issPosition?.longitude?.toDouble() ?: 0.0

        val distance = DistanceCalculatorInMiles.distance(lat1 = lat1, lon1 = long1, lat2=lat2, lon2 = long2)
        _distance.doubleValue = roundToTwoDecimalPoints(distance)
    }

    private fun roundToTwoDecimalPoints(distance: Double): Double {
        return (distance*100.0).roundToInt()/100.0
    }

}