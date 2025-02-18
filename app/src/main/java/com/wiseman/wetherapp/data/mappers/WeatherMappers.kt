package com.wiseman.wetherapp.data.mappers

import com.wiseman.wetherapp.data.remote.WeatherDataDto
import com.wiseman.wetherapp.data.remote.WeatherDto
import com.wiseman.wetherapp.domain.model.WeatherData
import com.wiseman.wetherapp.domain.model.WeatherInfo
import com.wiseman.wetherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {

    return time.mapIndexed { index: Int, time: String ->
        val temperature = temperatures[index]
        val pressure = pressure[index]
        val windSpeed = windspeed[index]
        val humidity = humidities[index]
        val weatherCode = weatherCodes[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }.also {
        it.entries.forEach{
            println()
        println("The current items - ${it.value}")
        }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val currentDateTime = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (currentDateTime.minute < 30) currentDateTime.hour else currentDateTime.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )

}