package com.csk.astromap.domain

import com.csk.astromap.BuildConfig
import com.csk.astromap.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(private val googleMapsRepo: GoogleMapsRepo) {
    suspend operator fun invoke(latlng: String): List<Result> = withContext(Dispatchers.IO){
            return@withContext googleMapsRepo.getAddressByLatLng(apiKey = BuildConfig.MAPS_API_KEY, latLng = latlng).results
        }
}