package com.wiseman.wetherapp.domain.repository

import arrow.core.Either
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.util.Failure
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(): Flow<Either<Failure, WeatherInfo>>
}