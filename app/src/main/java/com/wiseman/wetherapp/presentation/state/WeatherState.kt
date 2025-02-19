package com.wiseman.wetherapp.presentation.state

import com.wiseman.wetherapp.domain.model.WeatherInfo

sealed class WeatherState {
    data class Success(
        val data: WeatherInfo
    ) : WeatherState()
    data object Loading : WeatherState()
    data class Error(val failure: com.wiseman.wetherapp.util.Failure? = null) : WeatherState()
}
