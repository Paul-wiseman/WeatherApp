package com.wiseman.wetherapp.data.repository

import arrow.core.Either
import com.wiseman.wetherapp.data.location.LocationTracker
import com.wiseman.wetherapp.data.mappers.toWeatherInfo
import com.wiseman.wetherapp.data.remote.WeatherApi
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.util.Failure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApi,
    private val locationTracker: LocationTracker,
    private val coroutineDispatcher: CoroutineDispatcher,
) : WeatherRepository {
    override suspend fun getWeatherData(): Flow<Either<Failure, WeatherInfo>> = flow {
        try {
            when (val location = locationTracker.getCurrentLocation()) {
                is Either.Right -> {
                    emit(
                        Either.Right(
                            weatherApiService.getWeatherData(
                                lat = location.value.latitude,
                                long = location.value.longitude
                            ).toWeatherInfo()
                        )
                    )
                }

                is Either.Left -> {
                    emit(Either.Left(location.value))
                }
            }
        } catch (e: Exception) {
            emit(
                Either.Left(
                    Failure.NetworkError(
                        message = e.message
                            ?: "An failure has occurred trying to fetch weather data"
                    )
                )
            )
        }
    }.flowOn(coroutineDispatcher)
}