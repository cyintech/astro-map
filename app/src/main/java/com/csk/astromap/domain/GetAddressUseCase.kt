package com.csk.astromap.domain

import com.csk.astromap.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(private val googleMapsRepo: GoogleMapsRepo) {
    suspend operator fun invoke(latlng: String): List<Result> = withContext(Dispatchers.IO){
            return@withContext googleMapsRepo.getAddressByLatLng(apiKey = "AIzaSyCkDFcEOVDQctO5C6DmTKL5E5_Y3ZVDn_k", latLng = latlng).results
        }
}