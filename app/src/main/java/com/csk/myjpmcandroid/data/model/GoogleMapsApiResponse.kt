package com.csk.myjpmcandroid.data.model

data class GoogleMapsApiResponse(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
)