package com.wiseman.wetherapp.util

import android.content.Context
import androidx.annotation.StringRes

sealed class LocationError {
    data class LocationPermissionError(@StringRes val message: Int) : LocationError()
    data class NullLocationError(@StringRes val message: Int) : LocationError()

    fun toString(context: Context): String = when (this) {
        is LocationPermissionError -> context.getString(message)
        is NullLocationError -> context.getString(message)
    }
}