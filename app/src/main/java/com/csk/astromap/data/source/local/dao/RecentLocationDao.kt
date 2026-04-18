package com.csk.astromap.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.csk.astromap.data.source.local.model.UserISSDistance
import kotlinx.coroutines.flow.Flow

/**
 * This Dao is used to insert and read all the data in local db.
 * Keeping it simple for the usecase of this app.
 * otherwise, we can add delete, fetch particular record, delete all etc.
 */

@Dao
interface RecentLocationDao {

    @Upsert
    fun insert(userISSDistance: UserISSDistance)

    @Query("SELECT * from UserISSDistance")
    fun readAll(): Flow<List<UserISSDistance>>
}