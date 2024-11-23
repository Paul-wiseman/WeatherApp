package com.wiseman.wetherapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wiseman.wetherapp.presentation.components.WeatherCard
import com.wiseman.wetherapp.presentation.components.WeatherForecast
import com.wiseman.wetherapp.presentation.state.WeatherState
import com.wiseman.wetherapp.ui.theme.DarkBlue
import com.wiseman.wetherapp.ui.theme.DeepBlue

@Composable
fun HomeScreen(
    state: WeatherState
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                WeatherCard(
                    state = state,
                    backgroundColor = DeepBlue
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecast(
                    state = state
                )
            }

            ShowCircularProgressBar(
                state.isLoading
            )

            ShowError(state.error)
        }
    }
}

context(BoxScope)
@Composable
private fun ShowCircularProgressBar(
    show: Boolean,
) {
    if (show) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

context(BoxScope)
@Composable
private fun ShowError(
    error: String?
) {

    error?.let {
        Text(
            text = error,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}