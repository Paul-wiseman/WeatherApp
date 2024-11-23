package com.wiseman.wetherapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wiseman.wetherapp.presentation.components.WeatherCard
import com.wiseman.wetherapp.presentation.components.WeatherForecast
import com.wiseman.wetherapp.presentation.state.WeatherState
import com.wiseman.wetherapp.presentation.viewmodel.WeatherViewModel
import com.wiseman.wetherapp.ui.theme.DarkBlue
import com.wiseman.wetherapp.ui.theme.DeepBlue

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel
) {
    HomeScreenContent(
        viewModel.state.collectAsStateWithLifecycle().value,
        viewModel::refreshWeatherData
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    weatherState: WeatherState,
    onRefresh: () -> Unit
) {
    Scaffold { innerPadding ->
        val refreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            modifier = Modifier
                .background(DarkBlue)
                .padding(innerPadding),
            state = refreshState,
            isRefreshing = weatherState.isRefreshing,
            onRefresh = onRefresh
        ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())

                ) {
                    WeatherCard(
                        state = weatherState,
                        backgroundColor = DeepBlue
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    WeatherForecast(
                        state = weatherState
                    )
                }

                ShowCircularProgressBar(
                    Modifier.align(Alignment.Center),
                    weatherState.isLoading,
                )

                ShowError(
                    weatherState.error?.weatherErrorString(LocalContext.current),
                    Modifier.align(Alignment.Center)
                )
            }
        }

}


@Composable
private fun ShowCircularProgressBar(
    modifier: Modifier,
    show: Boolean,
) {
    if (show) {
        CircularProgressIndicator(
            modifier = modifier
        )
    }
}

@Composable
private fun ShowError(
    error: String?,
    modifier: Modifier
) {

    error?.let {
        Text(
            text = error,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    }
}