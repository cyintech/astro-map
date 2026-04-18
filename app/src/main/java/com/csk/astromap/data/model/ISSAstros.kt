package com.csk.astromap.data.model

/**
 * Below are the data classes to convert json response into kotlin objects from the endpoints
 * that return the information of currently on board astronauts of the ISS.
 */

data class ISSAstros(
    val message: String,
    val number: Int,
    val people: List<People>
)

data class People(
    val craft: String,
    val name: String
)