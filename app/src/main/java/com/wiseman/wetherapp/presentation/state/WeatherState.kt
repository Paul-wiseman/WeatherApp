package com.wiseman.wetherapp.presentation.state

import com.wiseman.wetherapp.domain.model.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
