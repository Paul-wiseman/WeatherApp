package com.wiseman.wetherapp.domain.repository

import arrow.core.Either
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.presentation.state.WeatherError

interface WeatherRepository {
    suspend fun getWeatherData(): Either<WeatherError, WeatherInfo>
}