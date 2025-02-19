package com.wiseman.wetherapp.data.location

import arrow.core.Either
import com.wiseman.wetherapp.util.Failure

interface LocationTracker {
    suspend fun getCurrentLocation(): Either<Failure, LocationData>
}