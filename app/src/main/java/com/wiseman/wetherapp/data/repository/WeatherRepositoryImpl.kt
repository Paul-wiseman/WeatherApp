package com.wiseman.wetherapp.data.repository

import android.util.Log
import com.wiseman.wetherapp.data.mappers.toWeatherInfo
import com.wiseman.wetherapp.data.remote.WeatherApi
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.util.Resources
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Resources<WeatherInfo> {
        return try {
            Log.i("Weather", "apiRequest  result -- ${api.getWeatherData(latitude,longitude)} ")
            Resources.Success(
                data = api.getWeatherData(
                    lat = latitude,
                    long = longitude
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resources.Error(message = e.message ?: "An unknown error occurred")
        }
    }
}