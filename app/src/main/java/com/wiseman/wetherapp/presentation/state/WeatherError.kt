package com.wiseman.wetherapp.presentation.state

import android.content.Context
import androidx.annotation.StringRes

sealed class WeatherError {
    data class LocationError(@StringRes val message: Int) : WeatherError()
    data class NetworkError(val message: String) : WeatherError()
    data class LocationPermissionError(@StringRes val message: Int) : WeatherError()

    fun weatherErrorString(context: Context): String = when (this) {
        is LocationError -> context.getString(message)
        is NetworkError -> message
        is LocationPermissionError -> context.getString(message)
    }
}