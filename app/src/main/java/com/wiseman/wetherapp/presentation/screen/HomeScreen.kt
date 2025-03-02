package com.wiseman.wetherapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wiseman.wetherapp.presentation.components.ShowLoadingIcon
import com.wiseman.wetherapp.presentation.components.ShowWeatherCard
import com.wiseman.wetherapp.presentation.components.WeatherForecast
import com.wiseman.wetherapp.presentation.state.WeatherState
import com.wiseman.wetherapp.presentation.viewmodel.WeatherViewModel
import com.wiseman.wetherapp.ui.theme.DarkBlue
import com.wiseman.wetherapp.ui.theme.DeepBlue
import com.wiseman.wetherapp.ui.theme.LocalSpacing

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel
) {
    HomeScreenContent(
        viewModel.weatherDataUiState.collectAsStateWithLifecycle().value,
        viewModel::refreshWeatherData,
        viewModel.isRefreshing.collectAsStateWithLifecycle().value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    weatherState: WeatherState,
    onRefreshCallback: () -> Unit,
    isRefreshing: Boolean
) {
    Scaffold { innerPadding ->
        val refreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            modifier = Modifier
                .background(DarkBlue)
                .padding(innerPadding),
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefreshCallback
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

            ) {

                when (weatherState) {
                    is WeatherState.Error -> ShowError(
                        weatherState.failure.toString(),
                    )

                    WeatherState.Loading -> ShowLoadingIcon()
                    is WeatherState.Success -> {
                        weatherState.data.currentWeatherData?.let { data ->
                            ShowWeatherCard(
                                weatherData = data,
                                backgroundColor = DeepBlue
                            )
                        }
                        Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))
                        WeatherForecast(
                            weatherInfo = weatherState.data
                        )
                    }
                }

            }
        }
    }

}

@Composable
private fun ShowError(
    error: String?
) {
    error?.let {
        Text(
            text = error,
            color = Color.Red,
            textAlign = TextAlign.Center,
        )
    }
}