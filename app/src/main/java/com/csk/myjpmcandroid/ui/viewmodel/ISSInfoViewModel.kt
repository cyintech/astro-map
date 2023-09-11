package com.csk.myjpmcandroid.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csk.myjpmcandroid.domain.ISSInfoRepository
import com.csk.myjpmcandroid.domain.RecentLocationRepository
import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation
import com.csk.myjpmcandroid.data.source.local.model.UserISSDistance
import com.csk.myjpmcandroid.model.UserAndISSLocation
import com.csk.myjpmcandroid.model.UserLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

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

    private var _recentData = MutableStateFlow(RecentItemUiState())
    var recentData : StateFlow<RecentItemUiState> = _recentData

    fun setAskPermission(ask: Boolean){
        _askPermission.value = ask
    }

    init {
        makeApiCall()
    }

    init {
        getISSLocation()
    }

    init {
        readAllDataFromDb()
    }

    private fun makeApiCall() = viewModelScope.launch {
        try {
            val issLocation = issInfoRepository.getISSLocation()
            val issAstros = issInfoRepository.getISSOnBoardAstronauts()

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

    // This read all the data from the local db but we only get 5 latest records to show on the UI
    fun readAllDataFromDb(){

        viewModelScope.launch {
            try {
                val result = recentLocationRepository.getAllLocations().map {
                    RecentItemUiState(it.takeLast(10))
                }
                recentData = result.stateIn(
                    this,
                    SharingStarted.WhileSubscribed(5000),
                    RecentItemUiState()
                )

            }catch (ioException: IOException){
                _recentData.value = RecentItemUiState(itemList = emptyList())
            }

        }
    }

    fun askPermission(ask: Boolean) { _askPermission.value=ask}

    fun updateUserLocation(userLocation: UserLocation){
        _userLocation.value = userLocation
    }

}

data class RecentItemUiState(
    val itemList: List<UserISSDistance> = listOf()
)