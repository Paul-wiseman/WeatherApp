package com.wiseman.wetherapp.domain.repository

import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.util.Resources

interface WeatherRepository {
    suspend fun getWeatherData(latitude: Double, longitude: Double): Resources<WeatherInfo>
}