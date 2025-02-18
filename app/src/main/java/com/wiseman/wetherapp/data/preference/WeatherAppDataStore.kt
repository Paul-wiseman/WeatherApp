package com.wiseman.wetherapp.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherAppDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationPreference {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)

    override suspend fun storeLocation(location: UserLocation) {
        context.dataStore.edit { store: MutablePreferences ->
            store[LONG_KEY] = location.longitude ?: 0.0
            store[LAT_KEY] = location.latitude ?: 0.0
        }
    }

    override fun getLocation(): Flow<UserLocation> = flow {
        context.dataStore.data.map { value: Preferences ->
            val longitude = value[LONG_KEY]
            val latitude = value[LAT_KEY]
            emit(
                UserLocation(
                    latitude = latitude,
                    longitude = longitude
                )
            )
        }
    }

    private companion object {
        const val NAME = "com.wiseman.weatherapp.preference"
        val LONG_KEY = doublePreferencesKey("location.longitude")
        val LAT_KEY = doublePreferencesKey("location.latitude")
    }
}