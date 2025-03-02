package com.wiseman.wetherapp.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.wiseman.wetherapp.data.works.RainNotificationWorker
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.notification.WeatherAppNotification
import javax.inject.Inject


class HiltWorkerFactory @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherAppNotification: WeatherAppNotification
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker =
        RainNotificationWorker(
            repository,
            appContext,
            workerParameters,
            weatherAppNotification
        )
}
