package com.csk.myjpmcandroid.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.csk.myjpmcandroid.R
import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation
import com.csk.myjpmcandroid.data.model.ISSPosition
import com.csk.myjpmcandroid.data.model.People
import com.csk.myjpmcandroid.model.UserAndISSLocation
import com.csk.myjpmcandroid.model.UserLocation
import com.csk.myjpmcandroid.ui.theme.MyJPMCAndroidTheme
import com.csk.myjpmcandroid.ui.viewmodel.ISSInfo
import com.csk.myjpmcandroid.ui.viewmodel.ISSInfoUiState
import com.csk.myjpmcandroid.ui.viewmodel.ISSInfoViewModel
import com.csk.myjpmcandroid.ui.viewmodel.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AlertDialogForLocationPermission(isShowing: Boolean, onProceedClick: () -> Unit) {
    val openDialog = remember { mutableStateOf(isShowing) }
    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.alert_dialog_for_location_permission_title))
            },
            text = {
                Text(stringResource(id = R.string.alert_dialog_for_location_permission_msg))
            },

            confirmButton = {
                Button(
                    onClick = {
                        onProceedClick()
                        openDialog.value = false
                    }
                    ) {
                    Text(stringResource(id = R.string.proceed), textAlign = TextAlign.Center)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(stringResource(id = R.string.no_thanks), textAlign = TextAlign.Center)
                }
            }
        )
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onUserLocationChange: (UserLocation)->Unit) {

        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.lastLocation.addOnCompleteListener{
            val location: Location? = it.result
            if(location!=null){
                onUserLocationChange(UserLocation(latitude = location.latitude, longitude = location.longitude))
            }else{
                onUserLocationChange(UserLocation())
            }
        }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(issInfoViewModel: ISSInfoViewModel, modifier: Modifier = Modifier) {

    when (val issInfoUiState: ISSInfoUiState = issInfoViewModel.issInfoUiState.collectAsStateWithLifecycle().value) {
        is ISSInfoUiState.Loading -> ProgressBarLoader()
        is ISSInfoUiState.Success -> SuccessScreen(issInfo = issInfoUiState.issInfo, modifier = modifier)
        is ISSInfoUiState.Error -> ErrorScreen()
    }

}

@Composable
fun ProgressBarLoader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            strokeWidth = 4.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    MyJPMCAndroidTheme {
        ProgressBarLoader()
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        LocationOnMAP(null)
        DistanceInMiles(0.0)
        RecentLocations(UserAndISSLocation(null,null), 0.0)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SuccessScreen(issInfo: ISSInfo, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val locationViewModel: LocationViewModel = viewModel()

    var userAndISSLocation by remember {
        mutableStateOf(UserAndISSLocation(issLocation = issInfo.issLocation))
    }

    var distance by remember {
        mutableDoubleStateOf(0.0)
    }

    distance = locationViewModel.distance.doubleValue

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        UserLocationAndISSLocation(userAndISSLocation.copy(issLocation = issInfo.issLocation))
        LocationOnMAP(issLocation = issInfo.issLocation!!)
        DistanceInMiles(distance)
        RecentLocations(userAndISSLocation, distance = distance)
    }
    val isLocationPermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
    val askPermission = remember {
        mutableStateOf(false)
    }
    AlertDialogForLocationPermission(!isLocationPermissionGranted) {
        askPermission.value = true
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if(isGranted){
            getCurrentLocation(context){userLocation->
                Log.i("HomeScreen", "User loc = $userLocation")
                userAndISSLocation = userAndISSLocation.copy(userLocation = userLocation, issLocation = issInfo.issLocation)
                locationViewModel.calculateDistance(userAndISSLocation)
            }
        }else{
            showToast(context)
        }
    }

    if (askPermission.value) {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    if(isLocationPermissionGranted){
        getCurrentLocation(context){userLocation->
            Log.i("HomeScreen", "User loc = $userLocation")
            userAndISSLocation = userAndISSLocation.copy(userLocation = userLocation, issLocation = issInfo.issLocation)
            locationViewModel.calculateDistance(userAndISSLocation)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLocationAndISSLocation(userAndISSLocation: UserAndISSLocation) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = "lat = ${userAndISSLocation.userLocation?.latitude}, lng = ${userAndISSLocation.userLocation?.longitude}",
                onValueChange = {},
                readOnly = true,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.your_location
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                }
                , modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.column_padding))
            )

            TextField(
                value = "lat = ${userAndISSLocation.issLocation?.issPosition?.latitude}, lng = ${userAndISSLocation.issLocation?.issPosition?.longitude}",
                onValueChange = {},
                readOnly = true,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.iss_location
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.column_padding))
            )}
}

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

@Composable
fun DistanceInMiles(distance: Double) {
    Text(
        text = stringResource(id = R.string.Distance, "$distance"),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.column_padding))
    )
}

@Composable
fun RecentLocations(userAndISSLocation: UserAndISSLocation, distance: Double, modifier: Modifier = Modifier) {
    val list = listOf<RecentLocation>(
        RecentLocation(UserAndISSLocation(UserLocation(111.22333, 12122.7), ISSLocation(ISSPosition("-20302.332","838383.34"), message = "success", timestamp = 1212)),12121.0),
        RecentLocation(UserAndISSLocation(UserLocation(111.22333, 12122.7), ISSLocation(ISSPosition("-20302.332","838383.34"), message = "success", timestamp = 1212)),12121.0),
        RecentLocation(UserAndISSLocation(UserLocation(111.22333, 12122.7), ISSLocation(ISSPosition("-20302.332","838383.34"), message = "success", timestamp = 1212)),12121.0),
        RecentLocation(UserAndISSLocation(UserLocation(111.22333, 12122.7), ISSLocation(ISSPosition("-20302.332","838383.34"), message = "success", timestamp = 1212)),12121.0),
        RecentLocation(UserAndISSLocation(UserLocation(111.22333, 12122.7), ISSLocation(ISSPosition("-20302.332","838383.34"), message = "success", timestamp = 1212)),12121.0)
    )
    LazyColumn{
        items(list){ recentLocation ->
            RecentLocationCard(recentLocation = recentLocation)
        }
    }
}

@Composable
fun RecentLocationCard(recentLocation: RecentLocation) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(contentColor = Color.DarkGray, containerColor = Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.Start, modifier = Modifier.padding(4.dp)) {
            val user = recentLocation.userAndISSLocation.userLocation!!
            val iss = recentLocation.userAndISSLocation.issLocation?.issPosition!!
            ShowRecentDataText(text = "Your Location: lat= ${user.latitude}, long= ${user.longitude}")
            ShowRecentDataText(text = "ISS Location: lat= ${iss.latitude}, long= ${iss.longitude}")
            ShowRecentDataText(text =  "Distance = ${recentLocation.distance} miles")
        }
    }
}

@Composable
fun ShowRecentDataText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.text_data_padding)),
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun RecentLocationPreview() {
    MyJPMCAndroidTheme {
        val recent = RecentLocation(UserAndISSLocation(UserLocation(111.22333, 12122.7),ISSLocation(ISSPosition("-20302.332","838383.34"), message = "success", timestamp = 1212)),0.0)
        RecentLocationCard(recent)
    }
}

// This method to call when the location permission is permanently denied by the user
// typically a permission is denied permanently if denied twice by the user at the same time
// In this use case, we would just want the user to allow it first in order to see the user location
private fun showSettings(context: Context) {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    context.startActivity(intent)
}

private fun showToast(context: Context) {
    Toast.makeText(context, "Oops, We can't proceed without location access!", Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun HomeScreenPreview() {
    val issInfo = ISSInfo(
        issLocation = ISSLocation(
            issPosition = ISSPosition(
                latitude = "25.1697",
                longitude = "-101.9786"
            ),
            message = "success",
            timestamp = 1694229254
        ),
        issAstros = ISSAstros(
            message = "success",
            number = 2,
            people = listOf(
                People(craft = "ISS", name = "Sergey Prokopyev"),
                People(craft = "ISS", name = "Dmitry Petelin")
            )
        )
    )
    MyJPMCAndroidTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
           SuccessScreen(issInfo = issInfo)
        }
    }
}

data class RecentLocation(
    val userAndISSLocation: UserAndISSLocation = UserAndISSLocation(),
    val distance: Double = 0.0
)

const val PermanentlyDenied = 2