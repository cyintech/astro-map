package com.csk.astromap.di.module

import com.csk.astromap.BuildConfig
import com.csk.astromap.data.GoogleMapsRepoImpl
import com.csk.astromap.domain.ISSInfoRepository
import com.csk.astromap.data.source.network.ISSNetworkApi
import com.csk.astromap.data.ISSInfoRepositoryImpl
import com.csk.astromap.data.source.network.GoogleMapsNetworkApi
import com.csk.astromap.domain.GetAddressUseCase
import com.csk.astromap.domain.GoogleMapsRepo
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

    @Provides
    @Singleton
    fun provideGoogleMapsNetworkApi(): GoogleMapsNetworkApi{
        return retrofit.create(GoogleMapsNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGoogleMapsRepo(googleMapsNetworkApi: GoogleMapsNetworkApi): GoogleMapsRepo{
        return GoogleMapsRepoImpl(googleMapsNetworkApi)
    }

    @Provides
    @Singleton
    fun provideGetAddressUseCase(googleMapsRepo: GoogleMapsRepo): GetAddressUseCase{
        return GetAddressUseCase(googleMapsRepo)
    }
}