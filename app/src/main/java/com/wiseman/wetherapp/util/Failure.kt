package com.wiseman.wetherapp.util

sealed class Failure {
    data class LocationError(val message: String = UNABLE_TO_GET_LOCATION_ERROR) : Failure()
    data class NetworkError(val message: String) : Failure()
    data class LocationPermissionError(val message: String = LOCATION_PERMISSION_ERROR) : Failure()

     companion object {
        const val UNABLE_TO_GET_LOCATION_ERROR =
            "Error trying to read your current Location, please retry again"
        const val LOCATION_PERMISSION_ERROR =
            "Couldn't retrieve location. Please grant Location permission and enable GPS"
    }
}