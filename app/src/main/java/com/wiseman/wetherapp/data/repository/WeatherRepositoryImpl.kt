package com.wiseman.wetherapp.data.repository

import arrow.core.Either
import com.wiseman.wetherapp.data.mappers.toWeatherInfo
import com.wiseman.wetherapp.data.remote.WeatherApi
import com.wiseman.wetherapp.domain.LocationTracker
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.state.WeatherError
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val locationTracker: LocationTracker
) : WeatherRepository {
    override suspend fun getWeatherData(): Either<WeatherError, WeatherInfo> {
        return try {
            return when (val location = locationTracker.getCurrentLocation()) {
                is Either.Right -> {
                    Either.Right(
                        api.getWeatherData(
                            lat = location.value.latitude,
                            long = location.value.longitude
                        ).toWeatherInfo()
                    )
                }

                is Either.Left -> {
                    Either.Left(location.value)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(
                WeatherError.NetworkError(
                    message = e.message ?: "An error has occurred trying to fetch weather data"
                )
            )
        }
    }
}