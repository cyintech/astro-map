package com.csk.astromap.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.csk.astromap.R
import com.csk.astromap.data.model.ISSLocation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LocationOnMAP(issLocation: ISSLocation?, modifier: Modifier = Modifier) {
    val lat = issLocation?.issPosition?.latitude?.toDouble()!!
    val lng = issLocation.issPosition.longitude.toDouble()
    val latLng = LatLng(lat, lng)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 5f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.box_height_for_map))
            .background(Color.LightGray)
    ) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = latLng),
                title = "ISS location in $latLng",
                snippet = "Marker in $latLng"
            )

        }

    }
}