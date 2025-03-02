package com.wiseman.wetherapp.data.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import arrow.core.Either
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.notification.WeatherAppNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class RainNotificationWorker @AssistedInject constructor(
    private val repository: WeatherRepository,
    @Assisted applicationContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val weatherAppNotification: WeatherAppNotification
) :
    CoroutineWorker(applicationContext, workerParameters) {

    override suspend fun doWork(): Result {
        return when (val request = repository.getWeatherData()) {
            is Either.Left -> Result.failure()
            is Either.Right -> {
                getWeatherDataForNextHour(request)?.let {
                    sendWeatherNotification(it)
                }
                Result.success()
            }
        }
    }

    private fun getWeatherDataForNextHour(request: Either.Right<WeatherInfo>): WeatherData? {
        val weatherDetails: WeatherInfo = request.value
        val weatherData = weatherDetails.weatherDataPerDay[0]?.find { value ->
            val currentDateTime = LocalDateTime.now()
            val hour =
                if (currentDateTime.minute < 30) currentDateTime.hour else currentDateTime.hour + 1
            value.time.hour == hour
        }
        return weatherData
    }

    private fun sendWeatherNotification(weatherData: WeatherData) {
        weatherAppNotification.showNotification(weatherData)
    }
}