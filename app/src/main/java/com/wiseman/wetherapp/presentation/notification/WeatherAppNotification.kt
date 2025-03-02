package com.wiseman.wetherapp.presentation.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.wiseman.wetherapp.MainActivity
import com.wiseman.wetherapp.R
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.util.checkPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherAppNotification @Inject constructor(@ApplicationContext private val context: Context) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotificationChannel() {
        val channelName = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.description_text)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(weatherData: WeatherData) {
        val isPermissionGranted = context.checkPermission(Manifest.permission.POST_NOTIFICATIONS)
        val isARainyWeatherData = weatherData.weatherType.isRainyWeatherType
        if (isPermissionGranted && isARainyWeatherData) {
            notificationManager.notify(NNOTIFICATION_ID, createNotificationBuilder(weatherData))
        }
    }


    private fun createNotificationBuilder(weatherData: WeatherData): Notification {
        val notificationContentText = createNotificationContentText(weatherData)
        return NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(weatherData.weatherType.iconRes)
            .setContentTitle(context.resources.getString(R.string.notification_title))
            .setContentText(notificationContentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(createPendingIntent())
            .setAutoCancel(true)
            .build()
    }

    private fun createNotificationContentText(weatherData: WeatherData): String {
        val temperature = weatherData.temperature
        val description = weatherData.weatherType.weatherDesc
        val notificationContentText = String.format(
            context.resources.getString(R.string.notification_content_text),
            description,
            temperature.toString(),
            weatherData.time.toString()
        )
        return notificationContentText
    }

    private fun createPendingIntent(): PendingIntent? {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent
    }

    private companion object {
        const val CHANNEL_ID = "com.wiseman.weatherApp"
        const val REQUEST_CODE = 0
        const val NNOTIFICATION_ID = 1222
    }
}