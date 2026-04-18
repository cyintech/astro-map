package com.csk.astromap.domain

import com.csk.astromap.data.source.local.model.UserISSDistance
import kotlinx.coroutines.flow.Flow

interface RecentLocationRepository {
    fun getAllLocations(): Flow<List<UserISSDistance>>

    suspend fun insert(userISSDistance: UserISSDistance)
}