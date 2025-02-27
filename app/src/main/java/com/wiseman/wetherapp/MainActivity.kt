package com.wiseman.wetherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.wiseman.wetherapp.data.works.RainNotificationWorker
import com.wiseman.wetherapp.presentation.notification.WeatherAppNotification
import com.wiseman.wetherapp.presentation.screen.HomeScreen
import com.wiseman.wetherapp.presentation.viewmodel.WeatherViewModel
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme
import com.wiseman.wetherapp.util.makeToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAppNotification by lazy { WeatherAppNotification(this) }
    private lateinit var locationAccessPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializePermissionLaunchers()
        requestLocationPermission()
        requestNotificationPermission()
        scheduleRainNotificationWorker()

        setContent {
            WeatherAppTheme {
                HomeScreen(viewModel)
            }
        }
    }

    private fun initializePermissionLaunchers() {
        // locationAccessPermissionLauncher
        locationAccessPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allPermissionGranted = permissions.all { it.value }
            if (allPermissionGranted) {
                viewModel.loadWeatherInfo()
            } else {
                makeToast(
                    getString(R.string.location_permission_is_required)
                )
            }
        }

        // notificationPermissionLauncher
        notificationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted)
                    weatherAppNotification.createNotificationChannel()
                else {
                    makeToast(
                        getString(R.string.notification_permission_is_required)
                    )
                }
            }

    }

    private fun scheduleRainNotificationWorker() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val rainNotificationWorkRequest = PeriodicWorkRequestBuilder<RainNotificationWorker>(
            WORK_REQUEST_TIME_INTERVAL, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RAIN_NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            rainNotificationWorkRequest
        )
    }

    private fun requestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> weatherAppNotification.createNotificationChannel()

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> makeToast(
                getString(R.string.notification_permission_is_required),
            )

            else -> notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun requestLocationPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        }
        locationAccessPermissionLauncher.launch(
            permission
        )
    }

    private companion object {
        const val WORK_REQUEST_TIME_INTERVAL = 1L
        const val RAIN_NOTIFICATION_WORK_NAME = "rain_notification_work"
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {}
}