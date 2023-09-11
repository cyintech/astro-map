package com.csk.myjpmcandroid.domain

import com.csk.myjpmcandroid.data.RecentLocationRepository
import com.csk.myjpmcandroid.data.source.local.dao.RecentLocationDao
import com.csk.myjpmcandroid.data.source.local.model.UserISSDistance
import kotlinx.coroutines.flow.Flow

class RecentLocationRepositoryImpl(private val recentLocationDao: RecentLocationDao): RecentLocationRepository {
    override fun getAllLocations(): Flow<List<UserISSDistance>> = recentLocationDao.readAll()

    override suspend fun insert(userISSDistance: UserISSDistance) = recentLocationDao.insert(userISSDistance)
}