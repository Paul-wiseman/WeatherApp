package com.wiseman.wetherapp

import com.wiseman.wetherapp.data.location.LocationData
import com.wiseman.wetherapp.data.remote.WeatherDataDto
import com.wiseman.wetherapp.data.remote.WeatherDto

object TestUtil {
    fun getTestWeatherDto() = WeatherDto(
        weatherData = WeatherDataDto(
            time = listOf(),
            temperatures = listOf(),
            weatherCodes = listOf(),
            pressure = listOf(),
            windspeed = listOf(),
            humidities = listOf()
        )
    )

    fun getLocationData() = LocationData(latitude = 4.5, longitude = 6.7)
}