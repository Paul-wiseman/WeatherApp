package com.wiseman.wetherapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.weather.WeatherType
import com.wiseman.wetherapp.presentation.state.WeatherState
import com.wiseman.wetherapp.ui.theme.DarkBlue
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme
import java.time.LocalDateTime

@Composable
fun WeatherForecast(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo,
) {
    weatherInfo.weatherDataPerDay[0]?.let { data: List<WeatherData> ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Today",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                items(data) { item: WeatherData ->
                    HourlyWeatherDisplay(
                        modifier = Modifier
                            .height(
                                100.dp
                            )
                            .padding(horizontal = 16.dp),
                        weatherData = item
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewWeatherForecast() {
    WeatherAppTheme {
        WeatherForecast(
            modifier = Modifier.background(DarkBlue),
            weatherInfo = WeatherInfo(
                weatherDataPerDay = mapOf(
                    0 to listOf(
                        WeatherData(
                            time = LocalDateTime.now(),
                            temperature = 8.9,
                            pressure = 10.11,
                            windSpeed = 12.13,
                            humidity = 14.15,
                            weatherType = WeatherType.Foggy

                        )
                    )
                ),
                currentWeatherData = WeatherData(
                    time = LocalDateTime.now(),
                    temperature = 8.9,
                    pressure = 10.11,
                    windSpeed = 12.13,
                    humidity = 14.15,
                    weatherType = WeatherType.Overcast

                )

            ),
        )

    }
}