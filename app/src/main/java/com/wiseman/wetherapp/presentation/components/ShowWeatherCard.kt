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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseman.wetherapp.R
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.weather.WeatherType
import com.wiseman.wetherapp.ui.theme.LocalSpacing
import com.wiseman.wetherapp.ui.theme.LocalTextSize
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun ShowWeatherCard(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
    backgroundColor: Color = Color.Blue,
) {
    val localSpacing = LocalSpacing.current
    val localTextSize = LocalTextSize.current
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(localSpacing.space10),
        modifier = modifier.padding(localSpacing.spaceMedium)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(localSpacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val formatedDate = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
            Text(
                text = String.format(stringResource(R.string.date), formatedDate),
                modifier = Modifier
                    .align(Alignment.End),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
            Image(
                painter = painterResource(id = weatherData.weatherType.iconRes),
                contentDescription = weatherData.weatherType.weatherDesc,
                modifier = Modifier.width(localSpacing.space200)
            )
            Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
            Text(
                text = String.format(stringResource(R.string.temperature), weatherData.temperature),
                color = Color.White,
                fontSize = localTextSize.size50
            )
            Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
            Text(
                text = weatherData.weatherType.weatherDesc,
                color = Color.White,
                fontSize = localTextSize.size32
            )

            Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
            ShowWeatherIndicators(weatherData)
        }

    }
}

@Composable
private fun ShowWeatherIndicators(weatherData: WeatherData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        WeatherDataDisplay(
            value = weatherData.pressure.roundToInt(),
            unit = stringResource(R.string.pressure_unit),
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            textStyle = TextStyle(color = Color.White)
        )

        WeatherDataDisplay(
            value = weatherData.humidity.roundToInt(),
            unit = stringResource(R.string.percentage),
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            textStyle = TextStyle(color = Color.White)
        )

        WeatherDataDisplay(
            value = weatherData.windSpeed.roundToInt(),
            unit = stringResource(R.string.windspeed_unit),
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            textStyle = TextStyle(color = Color.White)
        )
    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun PreviewWeatherCard() {
    WeatherAppTheme {
        ShowWeatherCard(
            weatherData =
            WeatherData(
                time = LocalDateTime.now(),
                temperature = 8.9,
                pressure = 10.11,
                windSpeed = 12.13,
                humidity = 14.15,
                weatherType = WeatherType.Foggy
            ),
        )

    }
}