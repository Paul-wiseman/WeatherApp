package com.wiseman.wetherapp.domain

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}