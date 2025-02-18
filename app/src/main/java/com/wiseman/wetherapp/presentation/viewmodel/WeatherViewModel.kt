package com.wiseman.wetherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.wiseman.wetherapp.data.works.WorkStarter
import com.wiseman.wetherapp.domain.LocationTracker
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val workStarter: WorkStarter,
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val state: StateFlow<WeatherState> = _state

    fun loadWeatherInfo() {
        viewModelScope.launch {
            when (val result = repository.getWeatherData()) {
                is Either.Left -> {
                    _state.update {
                        WeatherState.Error(result.value)
                    }
                }

                is Either.Right -> {
                    _state.update {
                        WeatherState.Success(result.value)
                    }
                }
            }
            _isRefreshing.update { false }
        }
    }

    fun refreshWeatherData() {
        _isRefreshing.update { true }
        loadWeatherInfo()
    }

    fun startRainWorker() {
        workStarter()
    }
}