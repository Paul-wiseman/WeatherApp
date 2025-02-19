package com.wiseman.wetherapp.util

import android.content.Context
import androidx.annotation.StringRes

sealed class Failure {
    data class LocationError(@StringRes val message: Int) : Failure()
    data class NetworkError(val message: String) : Failure()
    data class LocationPermissionError(@StringRes val message: Int) : Failure()

    fun failureErrorToString(context: Context): String = when (this) {
        is LocationError -> context.getString(message)
        is NetworkError -> message
        is LocationPermissionError -> context.getString(message)
    }
}