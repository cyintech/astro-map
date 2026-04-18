package com.csk.astromap.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csk.astromap.domain.RecentLocationRepository
import com.csk.astromap.data.source.local.model.UserISSDistance
import com.csk.astromap.domain.GetAddressUseCase
import com.csk.astromap.model.UserAndISSLocation
import com.csk.astromap.util.DistanceCalculatorInMiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * This viewModel is just created for simple use case of calculating the distance in miles from latitude and longitude
 * Keeping the logic here for simplicity purpose and due to the time constraints
 */

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val recentLocationRepository: RecentLocationRepository,
    private val addressUseCase: GetAddressUseCase): ViewModel() {

    private var _distance = mutableDoubleStateOf(0.0)
    val distance = _distance

    fun calculateDistance(userAndISSLocation: UserAndISSLocation) {
        viewModelScope.launch {

            val lat1 = userAndISSLocation.userLocation?.latitude ?: 0.0
            val long1 = userAndISSLocation.userLocation?.longitude ?: 0.0
            val lat2 = userAndISSLocation.issLocation?.issPosition?.latitude?.toDouble() ?: 0.0
            val long2 = userAndISSLocation.issLocation?.issPosition?.longitude?.toDouble() ?: 0.0

            val distance = DistanceCalculatorInMiles.distance(lat1 = lat1, lon1 = long1, lat2=lat2, lon2 = long2)
            _distance.doubleValue = roundToTwoDecimalPoints(distance)

            val record = UserISSDistance(
                userLocation = "lat = $lat1, long = $long1",
                issLocation = "lat = $lat2, long = $long2",
                distance = roundToTwoDecimalPoints(distance).toString()
            )

            val userLatLng = "$lat1,$long1"
            val issLatLng = "$lat2,$long2"
            try {
                val userAddress = addressUseCase(userLatLng).first().formatted_address
                val issAddress = addressUseCase(issLatLng).first().formatted_address
                Log.i("HomeScreen","Address from GoogleMaps User = $userAddress")
                Log.i("HomeScreen","Address from GoogleMaps User = $issAddress")
            }catch (exception: Exception){
                Log.i("HomeScreen","Exception on Address from GoogleMaps")
            }


            withContext(Dispatchers.IO){
                recentLocationRepository.insert(record)
            }
        }

    }

    //The distance has too many digits after decimal so just round off to two
    private fun roundToTwoDecimalPoints(distance: Double): Double {
        return (distance*100.0).roundToInt()/100.0
    }

}