package com.wiseman.wetherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    private val _state = MutableStateFlow(WeatherState())
    val state = combine(_state, _isRefreshing)
    { weatherState: WeatherState, refreshingState: Boolean ->
        weatherState.copy(isRefreshing = refreshingState)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = WeatherState()
        )

    fun loadWeatherInfo() {
        viewModelScope.launch {

            _state.update { state: WeatherState ->
                state.copy(
                    isLoading = true,
                    error = null
                )
            }
            when (val result = repository.getWeatherData()) {
                is Either.Left -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            weatherInfo = null,
                            error = result.value
                        )
                    }
                }

                is Either.Right -> {
                    _state.update { state ->
                        state.copy(
                            weatherInfo = result.value,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }

            if (_isRefreshing.value) {
                _isRefreshing.update { false }
            }

        }
    }

    fun refreshWeatherData() {
        _isRefreshing.update { true }
        loadWeatherInfo()
    }
}