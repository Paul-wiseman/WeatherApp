package com.wiseman.wetherapp.data.preference

import kotlinx.coroutines.flow.Flow

interface LocationPreference {
    suspend fun storeLocation(location: UserLocation)
    fun getLocation(): Flow<UserLocation>
}