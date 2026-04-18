package com.csk.astromap.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class UserISSDistance(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userLocation: String,
    val issLocation: String,
    val distance: String
)
