package com.wiseman.wetherapp.presentation.state

import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.util.WeatherError

sealed class WeatherState {
    data class Success(
        val data: WeatherInfo
    ) : WeatherState()
    data object Loading : WeatherState()
    data class Error(val weatherError: WeatherError? = null) : WeatherState()
}
