package com.csk.myjpmcandroid.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csk.myjpmcandroid.data.ISSInfoRepository
import com.csk.myjpmcandroid.data.RecentLocationRepository
import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation
import com.csk.myjpmcandroid.data.source.local.model.UserISSDistance
import com.csk.myjpmcandroid.model.UserAndISSLocation
import com.csk.myjpmcandroid.model.UserLocation
import com.csk.myjpmcandroid.util.DistanceCalculatorInMiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject
import kotlin.math.roundToInt

sealed interface ISSInfoUiState {
    data class Success(val issInfo: ISSInfo) : ISSInfoUiState
    data object Loading : ISSInfoUiState
    data object Error : ISSInfoUiState
}

data class ISSInfo(
    val issLocation: ISSLocation? = null,
    val issAstros: ISSAstros? = null
)

@HiltViewModel
class ISSInfoViewModel @Inject constructor(
    private val issInfoRepository: ISSInfoRepository,
    private val recentLocationRepository: RecentLocationRepository
) : ViewModel() {

    private var _issInfoUiState = MutableStateFlow<ISSInfoUiState>(ISSInfoUiState.Loading)
    val issInfoUiState : StateFlow<ISSInfoUiState> = _issInfoUiState

    private var _askPermission = mutableStateOf(false)
    val askPermission = _askPermission

    private var _distance = mutableStateOf(0.0)
    val distance = _distance

    private var _userLocation = MutableStateFlow(UserLocation())
    val userLocation = _userLocation

    private var _userAndISSLocation = MutableStateFlow(UserAndISSLocation())
    val userAndISSLocation = _userAndISSLocation

    fun setAskPermission(ask: Boolean){
        _askPermission.value = ask
    }

    init {
        makeApiCall()
    }

    init {
        getISSLocation()
    }

    suspend fun insertData() {
        val user = _userAndISSLocation.value.userLocation!!
        val iss = _userAndISSLocation.value.issLocation!!
        val record = UserISSDistance(
            userLocation = "lat = ${user.latitude}, long = ${user.longitude}",
            issLocation = "lat = ${iss.issPosition.latitude}, long = ${iss.issPosition.longitude}",
            distance = _distance.toString()
        )

        recentLocationRepository.insert(record)
    }

    fun readAllDataFromDb(): StateFlow<RecentItemUiState> =
        recentLocationRepository.getAllLocations().map {
            RecentItemUiState(it)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecentItemUiState()
        )

    private fun makeApiCall() = viewModelScope.launch {
        try {
            val issLocation = issInfoRepository.getISSLocation()
            val issAstros = issInfoRepository.getISSOnBoardAstronauts()
            _userAndISSLocation.update {
                it.copy(issLocation = issLocation)
            }
            val issInfo = ISSInfo(issLocation = issLocation, issAstros = issAstros)
            _issInfoUiState.value = ISSInfoUiState.Success(issInfo = issInfo)
            Log.i("HomeScreen","from ViewModel: lat = ${issInfo.issLocation?.issPosition?.latitude}, long = ${issInfo.issLocation?.issPosition?.longitude}")
        } catch (ioException: IOException) {
            _issInfoUiState.value = ISSInfoUiState.Error
            Log.i("ERROR", "${ioException.message}")
        } catch (httpException: Exception) {
            _issInfoUiState.value = ISSInfoUiState.Error
            Log.i("ERROR", "${httpException.message}")
        }

        calculateDistance(_userAndISSLocation.value)
    }


    private fun getISSLocation() {
        val counter = flow<Int> {
            var start = 1
            while (start<=5){
                delay(1000)
                start++
                emit(start)
                if(start==5){
                    start = 0
                }
            }

        }

        viewModelScope.launch {
            counter.collect {
                if(it==5){
                    makeApiCall()
                }
            }
        }
    }



    fun askPermission(ask: Boolean) { _askPermission.value=ask}

    fun updateUserLocation(userLocation: UserLocation){
        _userLocation.value = userLocation
    }

    fun calculateDistance(userAndISSLocation: UserAndISSLocation) {

        val lat1 = userAndISSLocation.userLocation?.latitude ?: 0.0
        val long1 = userAndISSLocation.userLocation?.longitude ?: 0.0
        val lat2 = userAndISSLocation.issLocation?.issPosition?.latitude?.toDouble() ?: 0.0
        val long2 = userAndISSLocation.issLocation?.issPosition?.longitude?.toDouble() ?: 0.0

        val distance = DistanceCalculatorInMiles.distance(lat1 = lat1, lon1 = long1, lat2=lat2, lon2 = long2)
        _distance.value = roundToTwoDecimalPoints(distance)
    }

    private fun roundToTwoDecimalPoints(distance: Double): Double {
        return (distance*100.0).roundToInt()/100.0
    }

}

data class RecentItemUiState(
    val itemList: List<UserISSDistance> = listOf()
)