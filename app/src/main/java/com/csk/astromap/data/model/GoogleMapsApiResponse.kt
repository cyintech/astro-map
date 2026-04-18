package com.csk.astromap.data.model

data class GoogleMapsApiResponse(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
)