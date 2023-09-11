package com.csk.myjpmcandroid.domain

import com.csk.myjpmcandroid.data.source.local.model.UserISSDistance
import kotlinx.coroutines.flow.Flow

interface RecentLocationRepository {
    fun getAllLocations(): Flow<List<UserISSDistance>>

    suspend fun insert(userISSDistance: UserISSDistance)
}