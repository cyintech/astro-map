package com.csk.myjpmcandroid.di.module

import android.content.Context
import com.csk.myjpmcandroid.data.RecentLocationRepository
import com.csk.myjpmcandroid.data.source.local.RecentLocationDatabase
import com.csk.myjpmcandroid.data.source.local.dao.RecentLocationDao
import com.csk.myjpmcandroid.domain.RecentLocationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OfflineModule {

    @Provides
    @Singleton
    fun provideRecentDbInstance(@ApplicationContext context: Context): RecentLocationDatabase{
        return RecentLocationDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRecentDaoInstance(recentLocationDatabase: RecentLocationDatabase): RecentLocationDao{
        return recentLocationDatabase.recentDao()
    }

    @Provides
    @Singleton
    fun providesRecentRepo(recentLocationDao: RecentLocationDao): RecentLocationRepository{
        return RecentLocationRepositoryImpl(recentLocationDao)
    }
}