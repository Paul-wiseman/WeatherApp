package com.wiseman.wetherapp.di

import com.wiseman.wetherapp.data.remote.WeatherApi
import com.wiseman.wetherapp.data.repository.WeatherRepositoryImpl
import com.wiseman.wetherapp.data.location.LocationTracker
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.util.coroutine.DispatchProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun bindsRepository(
        weatherApiService: WeatherApi,
        dispatchProvider: DispatchProvider,
        locationTracker: LocationTracker
    ): WeatherRepository = WeatherRepositoryImpl(
        weatherApiService = weatherApiService,
        coroutineDispatcher = dispatchProvider.io,
        locationTracker = locationTracker
    )
}