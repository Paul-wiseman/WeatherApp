package com.wiseman.wetherapp.data.works

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class WorkStarter(context: Context) {

    private val workerManager = WorkManager.getInstance(context)

    operator fun invoke() {
        if (isRunning()) return

        workerManager.enqueue(rainNotificationWorkRequest)
    }

    private fun isRunning(): Boolean {
        val work = workerManager.getWorkInfosByTag(WORK_MANAGER_TAG).get()
        work.forEach { workInfo ->
            if (workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING)
                return true

        }
        return false
    }

    private val workConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    private val rainNotificationWorkRequest: WorkRequest =
        PeriodicWorkRequestBuilder<RainNotificationWorker>(10, TimeUnit.MINUTES)
            .addTag(WORK_MANAGER_TAG)

            .build()

    private companion object {
        const val WORK_MANAGER_TAG = "RainNotificationWorkManager"
    }

}