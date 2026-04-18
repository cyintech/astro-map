package com.csk.astromap.util

import com.csk.astromap.data.source.local.model.UserISSDistance

data class ItemUiState(
    val details: ItemDetails = ItemDetails()
)

data class ItemDetails(
    val id: Int = 0,
    val userLocation: String = "",
    val issLocation: String = "",
    val distance: String = ""
)

fun ItemDetails.toUserIssDistance() : UserISSDistance = UserISSDistance(
    id = id,
    userLocation = userLocation,
    issLocation = issLocation,
    distance = distance
)

fun UserISSDistance.toItemUiState(): ItemUiState = ItemUiState(
    details = this.toItemDetails()
)

fun UserISSDistance.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    userLocation = userLocation,
    issLocation = issLocation,
    distance = distance
)