package com.wiseman.wetherapp.domain

import android.location.Location
import arrow.core.Either
import com.wiseman.wetherapp.util.WeatherError

interface LocationTracker {
    suspend fun getCurrentLocation(): Either<WeatherError, Location>
}