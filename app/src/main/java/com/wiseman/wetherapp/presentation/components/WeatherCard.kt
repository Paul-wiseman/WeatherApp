package com.wiseman.wetherapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseman.wetherapp.R
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.weather.WeatherType
import com.wiseman.wetherapp.presentation.state.WeatherState
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    state: WeatherState,
    backgroundColor: Color = Color.Blue,
) {
    state.weatherInfo?.currentWeatherData?.let { data: WeatherData ->
        Card(
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Today ${
                        data.time.format(
                            DateTimeFormatter.ofPattern(
                                "HH:mm"
                            )
                        )
                    }",
                    modifier = Modifier
                        .align(Alignment.End),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = data.weatherType.weatherDesc,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperature}°C",
                    color = Color.White,
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    color = Color.White,
                    fontSize = 32.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = "hpa",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                        textStyle = TextStyle(color = Color.White)
                    )

                    WeatherDataDisplay(
                        value = data.humidity.roundToInt(),
                        unit = "%",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        textStyle = TextStyle(color = Color.White)
                    )

                    WeatherDataDisplay(
                        value = data.windSpeed.roundToInt(),
                        unit = "km/h",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }

        }

    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun PreviewWeatherCard() {
    WeatherAppTheme {
        WeatherCard(
            state = WeatherState(
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

                isLoading = false,
                error = null
            ),
            backgroundColor = Color(0xFF2187ab), modifier = Modifier,
        )

    }
}