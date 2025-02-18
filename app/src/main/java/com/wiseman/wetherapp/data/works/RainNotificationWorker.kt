package com.wiseman.wetherapp.data.works

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import arrow.core.Either
import com.wiseman.wetherapp.MainActivity
import com.wiseman.wetherapp.data.preference.LocationPreference
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.domain.weather.WeatherType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class RainNotificationWorker @AssistedInject constructor(
    private val repository: WeatherRepository,
    private val locationPreference: LocationPreference,
    @Assisted applicationContext: Context,
    @Assisted workerParameters: WorkerParameters
) :
    CoroutineWorker(applicationContext, workerParameters) {
    private var notificationIcon = WeatherType.SlightRain.iconRes
    private var notificationIconDescription = WeatherType.SlightRain.weatherDesc


    override suspend fun doWork(): Result {
//        Log.i(TAG, "doWork: is running --- ")
//        locationPreference.getLocation().collect {
//            when (val result = repository.getWeatherData()) {
//                is Either.Right -> {
//                    val now = LocalDateTime.now()
//                    val nextHour =
//                        now.withHour(now.hour + 1).withMinute(0).withSecond(0).withNano(0)
//                    val weatherDataString =
//                        result.value.weatherDataPerDay[0]?.find { it.time == nextHour }
//                    Log.i(TAG, "doWork: is running ---weatherDataString -- $weatherDataString ")
//
//                    weatherDataString?.let { weatherData: WeatherData ->
//                        createNotificationChannel()
//
//                        sendNotification()
//
//                    } ?: return Result.retry()
//
//                    return Result.success()
//                }
//
//                is Either.Left -> Result.failure()
//            }
//        }
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        Log.i(TAG, "getForegroundInfo:  ----- ")
        return ForegroundInfo(
            NOTIFICATION_ID, createNotification()
        )
    }

    private fun createNotification(): Notification {
        Log.i(TAG, "createNotification:  -----")
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(notificationIcon)
            .setContentTitle(notificationIconDescription)
            .setContentText("In the next hour there will be a $notificationIconDescription")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return notificationBuilder.build()
    }

    private fun sendNotification() {
        Log.i(TAG, "sendNotification: ------ ")
        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.i(TAG, "sendNotification: ----permission granted ---")
                notify(NOTIFICATION_ID, createNotification())
            } else {
                Log.i(TAG, "sendNotification: --- permission not granted ----")
                Toast.makeText(
                    applicationContext,
                    "Please Grant Notification Permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, NOTIFICATION_NAME, importance)
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    private companion object {
        const val TAG = "RainNotificationWorker"
        const val CHANNEL_ID = "Wiseman_weather_about_to_rain_notification"
        const val WEATHER_DATA_INFO = "Next_hour_weather_data"
        const val NOTIFICATION_NAME = "Wiseman_WeatherApp"
        const val REQUEST_CODE = 0
        const val NOTIFICATION_ID = 1224
        val listOfDrizzleWeather = listOf(
            WeatherType.LightDrizzle,
            WeatherType.ModerateRain,
            WeatherType.DenseDrizzle,
            WeatherType.LightFreezingDrizzle,
            WeatherType.DenseFreezingDrizzle,
            WeatherType.PartlyCloudy,
        )

        val lisOfRainyWeather = listOf(
            WeatherType.SlightRain,
            WeatherType.ModerateRain,
            WeatherType.HeavyRain,
            WeatherType.HeavyFreezingRain,
            WeatherType.DenseFreezingDrizzle,
            WeatherType.SlightRainShowers,
            WeatherType.ModerateRainShowers,
            WeatherType.ViolentRainShowers,
        )
    }
}