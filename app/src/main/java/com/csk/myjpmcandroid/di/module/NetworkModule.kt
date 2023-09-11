package com.csk.myjpmcandroid.di.module

import com.csk.myjpmcandroid.BuildConfig
import com.csk.myjpmcandroid.domain.ISSInfoRepository
import com.csk.myjpmcandroid.data.source.network.ISSNetworkApi
import com.csk.myjpmcandroid.data.ISSInfoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService by lazy {
        retrofit.create(ISSNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIssNetworkApi(): ISSNetworkApi {
        return apiService
    }

    @Provides
    @Singleton
    fun provideIssInfoRepository(networkApi: ISSNetworkApi): ISSInfoRepository {
        return ISSInfoRepositoryImpl(networkApi)
    }
}