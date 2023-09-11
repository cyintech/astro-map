package com.csk.myjpmcandroid.ui.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.csk.myjpmcandroid.R
import com.csk.myjpmcandroid.model.UserAndISSLocation
import com.csk.myjpmcandroid.model.UserLocation
import com.csk.myjpmcandroid.ui.Preview.PreviewData
import com.csk.myjpmcandroid.ui.screen.component.AskLocationPermissionButton
import com.csk.myjpmcandroid.ui.screen.component.DisplayCurrentISSAstros
import com.csk.myjpmcandroid.ui.screen.component.DistanceInMiles
import com.csk.myjpmcandroid.ui.screen.component.ISSLocationPreview
import com.csk.myjpmcandroid.ui.screen.component.LocationInfoField
import com.csk.myjpmcandroid.ui.screen.component.LocationOnMAP
import com.csk.myjpmcandroid.ui.screen.component.LocationOnMapPreview
import com.csk.myjpmcandroid.ui.screen.component.RecentLocations
import com.csk.myjpmcandroid.ui.screen.component.UserLocationPreview
import com.csk.myjpmcandroid.ui.theme.MyJPMCAndroidTheme
import com.csk.myjpmcandroid.ui.viewmodel.ISSInfo
import com.csk.myjpmcandroid.ui.viewmodel.LocationViewModel
import com.csk.myjpmcandroid.ui.viewmodel.RecentItemUiState

@Composable
fun SuccessScreen(
    issInfo: ISSInfo,
    recentItemUiState: RecentItemUiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val locationViewModel: LocationViewModel = hiltViewModel()

    var permissionDeniedCounter by remember{ mutableIntStateOf(0) }

    var userAndISSLocation by remember {
        mutableStateOf(UserAndISSLocation(issLocation = issInfo.issLocation))
    }

    var userLocationState by remember{ mutableStateOf(UserLocation()) }

    userAndISSLocation = userAndISSLocation.copy(userLocation = userLocationState,issLocation = issInfo.issLocation)

    var isLocationPermissionGranted by remember {
        mutableStateOf(false)
    }

    isLocationPermissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        isLocationPermissionGranted = isGranted
        if(!isGranted){
            permissionDeniedCounter++
            showToast(context)
        }
        if(permissionDeniedCounter== PERMANENTLY_DENIED){
            showSettings(context)
            permissionDeniedCounter = 0
        }
    }

    var distance by remember {
        mutableDoubleStateOf(0.0)
    }

    distance = locationViewModel.distance.doubleValue

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LocationInfoField(
            label = R.string.your_location,
           userAndISSLocation = userAndISSLocation.copy(userLocation = userLocationState)
        )
        LocationInfoField(
            label = R.string.iss_location,
           userAndISSLocation = userAndISSLocation.copy(issLocation = issInfo.issLocation)
        )
        LocationOnMAP(issLocation = issInfo.issLocation!!)
        if(isLocationPermissionGranted){
            DistanceInMiles(distance)
        }

        val currentOnBoardPeople = issInfo.issAstros?.people?.filter {it.craft == "ISS" } ?: emptyList()
        DisplayCurrentISSAstros(currentOnBoardPeople)

        if(!isLocationPermissionGranted){
            AskLocationPermissionButton{
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        RecentLocations(recentItemUiState.itemList)

    }

    if (isLocationPermissionGranted) {
        getCurrentLocation(context) { userLocation ->
            Log.i("HomeScreen", "User loc = $userLocation")
            userAndISSLocation = userAndISSLocation.copy(
                userLocation = userLocation,
                issLocation = issInfo.issLocation
            )
            userLocationState = userLocation
            locationViewModel.calculateDistance(userAndISSLocation)
        }
    }

}

// This method to call when the location permission is permanently denied by the user
// typically a permission is denied permanently if denied twice by the user at the same time
// In this use case, we would just want the user to allow it first in order to see the user location
private fun showSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts(context.getString(R.string.package_uri), context.packageName, null)
    context.startActivity(intent)
}

private fun showToast(context: Context) {
    Toast.makeText(context, "We can't proceed without location access!", Toast.LENGTH_SHORT)
        .show()
}

const val PERMANENTLY_DENIED = 2

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {

    MyJPMCAndroidTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            UserLocationPreview()
            ISSLocationPreview()
            LocationOnMapPreview()
            DistanceInMiles(distance = 0.0)
            DisplayCurrentISSAstros(currentAstros = PreviewData.issInfo.issAstros?.people!!)
            RecentLocations(recentItemList = PreviewData.recentItemUiState.itemList)
        }
    }
}