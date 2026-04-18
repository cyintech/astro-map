package com.csk.astromap.data

import com.csk.astromap.domain.RecentLocationRepository
import com.csk.astromap.data.source.local.dao.RecentLocationDao
import com.csk.astromap.data.source.local.model.UserISSDistance
import kotlinx.coroutines.flow.Flow

class RecentLocationRepositoryImpl(private val recentLocationDao: RecentLocationDao):
    RecentLocationRepository {
    override fun getAllLocations(): Flow<List<UserISSDistance>> = recentLocationDao.readAll()

    override suspend fun insert(userISSDistance: UserISSDistance) = recentLocationDao.insert(userISSDistance)
}