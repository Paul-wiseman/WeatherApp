package com.wiseman.wetherapp.domain.repository

import arrow.core.Either
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.util.Failure

interface WeatherRepository {
    suspend fun getWeatherData(): Either<Failure, WeatherInfo>
}