package com.wiseman.wetherapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.weather.WeatherType
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = weatherData.weatherType.weatherDesc,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperature}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHourlyWeatherDisplay() {
    WeatherAppTheme {
        HourlyWeatherDisplay(
            weatherData = WeatherData(
                time = LocalDateTime.now(),
                temperature = 8.9,
                pressure = 10.11,
                windSpeed = 12.13,
                humidity = 14.15,
                weatherType = WeatherType.Overcast

            )
        )
    }
}