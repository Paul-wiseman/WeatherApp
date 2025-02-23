package com.wiseman.wetherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    private val _weatherDataUiState= MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherDataUiState: StateFlow<WeatherState> = _weatherDataUiState

    fun loadWeatherInfo() {
        viewModelScope.launch {
            repository.getWeatherData().collectLatest { weatherData ->
                when (weatherData) {
                    is Either.Left -> {
                        _weatherDataUiState.update {
                            WeatherState.Error(weatherData.value)
                        }
                    }

                    is Either.Right -> {
                        _weatherDataUiState.update {
                            WeatherState.Success(weatherData.value)
                        }
                    }
                }
                _isRefreshing.update { false }
            }
        }
    }

    fun refreshWeatherData() {
        _isRefreshing.update { true }
        loadWeatherInfo()
    }
}