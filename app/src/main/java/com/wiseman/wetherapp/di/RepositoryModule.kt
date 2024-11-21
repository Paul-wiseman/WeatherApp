package com.wiseman.wetherapp.di

import com.wiseman.wetherapp.data.repository.WeatherRepositoryImpl
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(weatherTrackerRepository: WeatherRepositoryImpl): WeatherRepository
}