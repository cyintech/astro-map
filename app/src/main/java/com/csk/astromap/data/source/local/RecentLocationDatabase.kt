package com.csk.astromap.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.csk.astromap.data.source.local.dao.RecentLocationDao
import com.csk.astromap.data.source.local.model.UserISSDistance

@Database(entities = [UserISSDistance::class], version = 1, exportSchema = false)
abstract class RecentLocationDatabase : RoomDatabase() {

    abstract fun recentDao(): RecentLocationDao

    companion object {
        @Volatile
        private var Instance: RecentLocationDatabase? = null

        fun getDatabase(context: Context): RecentLocationDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    RecentLocationDatabase::class.java,
                    "recent_locationDb"
                )
                    .fallbackToDestructiveMigration().build().also {
                        Instance = it
                    }
            }
        }

    }


}