package com.csk.astromap.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.csk.astromap.model.UserLocation
import com.csk.astromap.ui.screen.component.ProgressBarLoader
import com.csk.astromap.ui.viewmodel.ISSInfoUiState
import com.csk.astromap.ui.viewmodel.ISSInfoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onUserLocationChange: (UserLocation) -> Unit) {

    val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationProviderClient.lastLocation.addOnCompleteListener {
        val location: Location? = it.result
        if (location != null) {
            onUserLocationChange(
                UserLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        } else {
            onUserLocationChange(UserLocation())
        }
    }
}

@Composable
fun HomeScreen(issInfoViewModel: ISSInfoViewModel, modifier: Modifier = Modifier) {

    val readData = issInfoViewModel.recentData.collectAsStateWithLifecycle().value
    when (val issInfoUiState: ISSInfoUiState = issInfoViewModel.issInfoUiState.collectAsStateWithLifecycle().value) {
        is ISSInfoUiState.Loading -> ProgressBarLoader()
        is ISSInfoUiState.Success -> SuccessScreen(
            issInfo = issInfoUiState.issInfo,
            recentItemUiState = readData,
            modifier = modifier
        )

        is ISSInfoUiState.Error -> ErrorScreen()
    }

}