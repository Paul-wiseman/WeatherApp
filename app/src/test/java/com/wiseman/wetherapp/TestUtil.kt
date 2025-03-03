package com.wiseman.wetherapp

import com.wiseman.wetherapp.data.location.LocationData
import com.wiseman.wetherapp.data.remote.WeatherDataDto
import com.wiseman.wetherapp.data.remote.WeatherDto

object TestUtil {

    private val time = listOf(
        "2025-02-23T00:00",
        "2025-02-23T01:00",
        "2025-02-23T02:00",
        "2025-02-23T03:00",
        "2025-02-23T04:00",
        "2025-02-23T05:00",
        "2025-02-23T06:00",
        "2025-02-23T07:00",
        "2025-02-23T08:00",
        "2025-02-23T09:00",
        "2025-02-23T10:00",
        "2025-02-23T11:00",
        "2025-02-23T12:00",
        "2025-02-23T13:00",
        "2025-02-23T14:00",
        "2025-02-23T15:00",
        "2025-02-23T16:00",
        "2025-02-23T17:00",
        "2025-02-23T18:00",
        "2025-02-23T19:00",
        "2025-02-23T20:00",
        "2025-02-23T21:00",
        "2025-02-23T22:00",
        "2025-02-23T23:00",
        "2025-02-24T00:00",
        "2025-02-24T01:00",
        "2025-02-24T02:00",
        "2025-02-24T03:00",
        "2025-02-24T04:00",
        "2025-02-24T05:00",
        "2025-02-24T06:00",
        "2025-02-24T07:00",
        "2025-02-24T08:00",
        "2025-02-24T09:00",
        "2025-02-24T10:00",
        "2025-02-24T11:00",
        "2025-02-24T12:00",
        "2025-02-24T13:00",
        "2025-02-24T14:00",
        "2025-02-24T15:00",
        "2025-02-24T16:00",
        "2025-02-24T17:00",
        "2025-02-24T18:00",
        "2025-02-24T19:00",
        "2025-02-24T20:00",
        "2025-02-24T21:00",
        "2025-02-24T22:00",
        "2025-02-24T23:00",
        "2025-02-25T00:00",
        "2025-02-25T01:00",
        "2025-02-25T02:00",
        "2025-02-25T03:00",
        "2025-02-25T04:00",
        "2025-02-25T05:00",
        "2025-02-25T06:00",
        "2025-02-25T07:00",
        "2025-02-25T08:00",
        "2025-02-25T09:00",
        "2025-02-25T10:00",
        "2025-02-25T11:00",
        "2025-02-25T12:00",
        "2025-02-25T13:00",
        "2025-02-25T14:00",
        "2025-02-25T15:00",
        "2025-02-25T16:00",
        "2025-02-25T17:00",
        "2025-02-25T18:00",
        "2025-02-25T19:00",
        "2025-02-25T20:00",
        "2025-02-25T21:00",
        "2025-02-25T22:00",
        "2025-02-25T23:00",
        "2025-02-26T00:00",
        "2025-02-26T01:00",
        "2025-02-26T02:00",
        "2025-02-26T03:00",
        "2025-02-26T04:00",
        "2025-02-26T05:00",
        "2025-02-26T06:00",
        "2025-02-26T07:00",
        "2025-02-26T08:00",
        "2025-02-26T09:00",
        "2025-02-26T10:00",
        "2025-02-26T11:00",
        "2025-02-26T12:00",
        "2025-02-26T13:00",
        "2025-02-26T14:00",
        "2025-02-26T15:00",
        "2025-02-26T16:00",
        "2025-02-26T17:00",
        "2025-02-26T18:00",
        "2025-02-26T19:00",
        "2025-02-26T20:00",
        "2025-02-26T21:00",
        "2025-02-26T22:00",
        "2025-02-26T23:00",
        "2025-02-27T00:00",
        "2025-02-27T01:00",
        "2025-02-27T02:00",
        "2025-02-27T03:00",
        "2025-02-27T04:00",
        "2025-02-27T05:00",
        "2025-02-27T06:00",
        "2025-02-27T07:00",
        "2025-02-27T08:00",
        "2025-02-27T09:00",
        "2025-02-27T10:00",
        "2025-02-27T11:00",
        "2025-02-27T12:00",
        "2025-02-27T13:00",
        "2025-02-27T14:00",
        "2025-02-27T15:00",
        "2025-02-27T16:00",
        "2025-02-27T17:00",
        "2025-02-27T18:00",
        "2025-02-27T19:00",
        "2025-02-27T20:00",
        "2025-02-27T21:00",
        "2025-02-27T22:00",
        "2025-02-27T23:00",
        "2025-02-28T00:00",
        "2025-02-28T01:00",
        "2025-02-28T02:00",
        "2025-02-28T03:00",
        "2025-02-28T04:00",
        "2025-02-28T05:00",
        "2025-02-28T06:00",
        "2025-02-28T07:00",
        "2025-02-28T08:00",
        "2025-02-28T09:00",
        "2025-02-28T10:00",
        "2025-02-28T11:00",
        "2025-02-28T12:00",
        "2025-02-28T13:00",
        "2025-02-28T14:00",
        "2025-02-28T15:00",
        "2025-02-28T16:00",
        "2025-02-28T17:00",
        "2025-02-28T18:00",
        "2025-02-28T19:00",
        "2025-02-28T20:00",
        "2025-02-28T21:00",
        "2025-02-28T22:00",
        "2025-02-28T23:00",
        "2025-03-01T00:00",
        "2025-03-01T01:00",
        "2025-03-01T02:00",
        "2025-03-01T03:00",
        "2025-03-01T04:00",
        "2025-03-01T05:00",
        "2025-03-01T06:00",
        "2025-03-01T07:00",
        "2025-03-01T08:00",
        "2025-03-01T09:00",
        "2025-03-01T10:00",
        "2025-03-01T11:00",
        "2025-03-01T12:00",
        "2025-03-01T13:00",
        "2025-03-01T14:00",
        "2025-03-01T15:00",
        "2025-03-01T16:00",
        "2025-03-01T17:00",
        "2025-03-01T18:00",
        "2025-03-01T19:00",
        "2025-03-01T20:00",
        "2025-03-01T21:00",
        "2025-03-01T22:00",
        "2025-03-01T23:00"
    )
    private val temperatures = listOf(
        4.8,
        5.2,
        5.5,
        5.5,
        5.6,
        5.8,
        6.6,
        7.5,
        8.0,
        9.1,
        10.0,
        11.4,
        11.1,
        12.0,
        12.3,
        11.7,
        11.4,
        10.7,
        9.3,
        8.5,
        7.6,
        5.1,
        3.0,
        3.2,
        3.2,
        3.0,
        2.7,
        2.8,
        2.7,
        2.1,
        2.0,
        1.6,
        1.9,
        3.9,
        7.1,
        9.9,
        12.0,
        13.0,
        13.3,
        13.2,
        12.7,
        11.8,
        11.0,
        10.4,
        10.0,
        9.7,
        9.3,
        9.0,
        8.8,
        8.5,
        8.3,
        8.0,
        8.0,
        7.8,
        7.7,
        7.8,
        8.0,
        8.4,
        8.8,
        10.0,
        11.0,
        11.4,
        11.3,
        11.1,
        10.6,
        10.0,
        9.6,
        9.2,
        9.0,
        8.8,
        7.7,
        7.5,
        7.2,
        6.9,
        6.5,
        6.4,
        6.3,
        6.3,
        6.4,
        6.5,
        6.6,
        6.8,
        7.8,
        7.9,
        7.9,
        7.8,
        7.5,
        7.0,
        6.8,
        6.5,
        6.1,
        5.6,
        4.9,
        4.4,
        3.7,
        3.0,
        2.5,
        1.9,
        1.2,
        0.6,
        -0.1,
        -0.8,
        -1.0,
        -0.6,
        0.2,
        1.5,
        3.7,
        6.4,
        8.4,
        9.3,
        9.4,
        9.1,
        8.4,
        7.2,
        6.1,
        5.2,
        4.4,
        3.7,
        3.3,
        3.1,
        2.9,
        2.7,
        2.7,
        2.6,
        2.3,
        2.1,
        2.2,
        2.7,
        3.5,
        4.6,
        6.1,
        7.8,
        9.0,
        9.4,
        9.3,
        8.9,
        8.0,
        6.7,
        5.8,
        4.2,
        3.9,
        3.6,
        3.1,
        2.4,
        1.9,
        1.9,
        2.1,
        2.1,
        1.9,
        1.6,
        1.5,
        1.8,
        2.4,
        3.1,
        4.0,
        5.2,
        6.0,
        6.3,
        6.4,
        6.2,
        5.9,
        5.3,
        4.8,
        4.3,
        3.7,
        3.3,
        2.9,
        2.7
    )
    private val weatherCodes = listOf(
        3,
        3,
        3,
        3,
        2,
        3,
        3,
        3,
        3,
        61,
        3,
        2,
        3,
        3,
        3,
        3,
        3,
        0,
        1,
        0,
        0,
        0,
        45,
        45,
        45,
        45,
        45,
        45,
        45,
        45,
        45,
        45,
        45,
        3,
        3,
        2,
        2,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        61,
        3,
        3,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        61,
        3,
        3,
        3,
        2,
        0,
        0,
        0,
        45,
        45,
        45,
        45,
        45,
        45,
        1,
        1,
        1,
        1,
        1,
        1,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        1,
        1,
        1,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3
    )
    private val pressure = listOf(
        7.0,
        5.5,
        5.8,
        5.1,
        5.5,
        4.8,
        5.8,
        8.8,
        9.7,
        11.2,
        11.1,
        13.5,
        13.2,
        9.7,
        10.8,
        9.4,
        6.6,
        5.4,
        2.8,
        1.8,
        2.0,
        3.3,
        3.1,
        3.8,
        4.4,
        5.0,
        6.0,
        5.5,
        6.7,
        7.3,
        8.1,
        7.4,
        7.5,
        8.6,
        9.0,
        10.1,
        12.2,
        12.3,
        12.6,
        12.6,
        11.5,
        10.8,
        10.8,
        11.6,
        10.9,
        10.2,
        10.2,
        9.8,
        10.2,
        10.1,
        10.1,
        9.4,
        8.6,
        7.6,
        6.9,
        6.0,
        5.1,
        4.6,
        4.0,
        6.3,
        5.4,
        6.0,
        5.2,
        4.3,
        5.7,
        6.0,
        6.0,
        5.7,
        6.1,
        5.1,
        4.8,
        5.9,
        6.6,
        6.6,
        6.2,
        4.9,
        4.0,
        3.6,
        3.8,
        3.6,
        3.8,
        5.0,
        10.2,
        11.8,
        12.7,
        13.5,
        13.1,
        13.2,
        11.7,
        9.7,
        8.4,
        5.9,
        4.3,
        4.0,
        3.6,
        3.4,
        3.1,
        3.2,
        3.3,
        3.6,
        3.7,
        3.7,
        3.8,
        4.3,
        4.4,
        4.9,
        5.6,
        6.4,
        7.3,
        7.6,
        7.3,
        7.0,
        6.4,
        5.2,
        5.0,
        4.8,
        4.8,
        4.4,
        4.0,
        3.3,
        2.9,
        2.9,
        3.0,
        3.2,
        3.1,
        3.3,
        3.1,
        2.8,
        2.6,
        2.9,
        4.7,
        7.6,
        9.4,
        10.0,
        9.5,
        8.9,
        7.3,
        5.2,
        3.7,
        5.7,
        6.8,
        7.8,
        7.4,
        7.0,
        6.4,
        6.6,
        6.9,
        6.9,
        6.1,
        5.3,
        4.8,
        5.2,
        6.2,
        6.5,
        6.3,
        5.5,
        5.1,
        5.6,
        6.3,
        6.3,
        5.4,
        3.7,
        2.9,
        3.1,
        3.7,
        4.4,
        4.9,
        4.9
    )
    private val windspeed = listOf(
        7.0,
        5.5,
        5.8,
        5.1,
        5.5,
        4.8,
        5.8,
        8.8,
        9.7,
        11.2,
        11.1,
        13.5,
        13.2,
        9.7,
        10.8,
        9.4,
        6.6,
        5.4,
        2.8,
        1.8,
        2.0,
        3.3,
        3.1,
        3.8,
        4.4,
        5.0,
        6.0,
        5.5,
        6.7,
        7.3,
        8.1,
        7.4,
        7.5,
        8.6,
        9.0,
        10.1,
        12.2,
        12.3,
        12.6,
        12.6,
        11.5,
        10.8,
        10.8,
        11.6,
        10.9,
        10.2,
        10.2,
        9.8,
        10.2,
        10.1,
        10.1,
        9.4,
        8.6,
        7.6,
        6.9,
        6.0,
        5.1,
        4.6,
        4.0,
        6.3,
        5.4,
        6.0,
        5.2,
        4.3,
        5.7,
        6.0,
        6.0,
        5.7,
        6.1,
        5.1,
        4.8,
        5.9,
        6.6,
        6.6,
        6.2,
        4.9,
        4.0,
        3.6,
        3.8,
        3.6,
        3.8,
        5.0,
        10.2,
        11.8,
        12.7,
        13.5,
        13.1,
        13.2,
        11.7,
        9.7,
        8.4,
        5.9,
        4.3,
        4.0,
        3.6,
        3.4,
        3.1,
        3.2,
        3.3,
        3.6,
        3.7,
        3.7,
        3.8,
        4.3,
        4.4,
        4.9,
        5.6,
        6.4,
        7.3,
        7.6,
        7.3,
        7.0,
        6.4,
        5.2,
        5.0,
        4.8,
        4.8,
        4.4,
        4.0,
        3.3,
        2.9,
        2.9,
        3.0,
        3.2,
        3.1,
        3.3,
        3.1,
        2.8,
        2.6,
        2.9,
        4.7,
        7.6,
        9.4,
        10.0,
        9.5,
        8.9,
        7.3,
        5.2,
        3.7,
        5.7,
        6.8,
        7.8,
        7.4,
        7.0,
        6.4,
        6.6,
        6.9,
        6.9,
        6.1,
        5.3,
        4.8,
        5.2,
        6.2,
        6.5,
        6.3,
        5.5,
        5.1,
        5.6,
        6.3,
        6.3,
        5.4,
        3.7,
        2.9,
        3.1,
        3.7,
        4.4,
        4.9,
        4.9
    )
    private val humidities = listOf(
        7.0,
        5.5,
        5.8,
        5.1,
        5.5,
        4.8,
        5.8,
        8.8,
        9.7,
        11.2,
        11.1,
        13.5,
        13.2,
        9.7,
        10.8,
        9.4,
        6.6,
        5.4,
        2.8,
        1.8,
        2.0,
        3.3,
        3.1,
        3.8,
        4.4,
        5.0,
        6.0,
        5.5,
        6.7,
        7.3,
        8.1,
        7.4,
        7.5,
        8.6,
        9.0,
        10.1,
        12.2,
        12.3,
        12.6,
        12.6,
        11.5,
        10.8,
        10.8,
        11.6,
        10.9,
        10.2,
        10.2,
        9.8,
        10.2,
        10.1,
        10.1,
        9.4,
        8.6,
        7.6,
        6.9,
        6.0,
        5.1,
        4.6,
        4.0,
        6.3,
        5.4,
        6.0,
        5.2,
        4.3,
        5.7,
        6.0,
        6.0,
        5.7,
        6.1,
        5.1,
        4.8,
        5.9,
        6.6,
        6.6,
        6.2,
        4.9,
        4.0,
        3.6,
        3.8,
        3.6,
        3.8,
        5.0,
        10.2,
        11.8,
        12.7,
        13.5,
        13.1,
        13.2,
        11.7,
        9.7,
        8.4,
        5.9,
        4.3,
        4.0,
        3.6,
        3.4,
        3.1,
        3.2,
        3.3,
        3.6,
        3.7,
        3.7,
        3.8,
        4.3,
        4.4,
        4.9,
        5.6,
        6.4,
        7.3,
        7.6,
        7.3,
        7.0,
        6.4,
        5.2,
        5.0,
        4.8,
        4.8,
        4.4,
        4.0,
        3.3,
        2.9,
        2.9,
        3.0,
        3.2,
        3.1,
        3.3,
        3.1,
        2.8,
        2.6,
        2.9,
        4.7,
        7.6,
        9.4,
        10.0,
        9.5,
        8.9,
        7.3,
        5.2,
        3.7,
        5.7,
        6.8,
        7.8,
        7.4,
        7.0,
        6.4,
        6.6,
        6.9,
        6.9,
        6.1,
        5.3,
        4.8,
        5.2,
        6.2,
        6.5,
        6.3,
        5.5,
        5.1,
        5.6,
        6.3,
        6.3,
        5.4,
        3.7,
        2.9,
        3.1,
        3.7,
        4.4,
        4.9,
        4.9
    )

    fun getWeatherDataDto(): WeatherDataDto = WeatherDataDto(
        time = time,
        temperatures = temperatures,
        weatherCodes = weatherCodes,
        pressure = pressure,
        windspeed = windspeed,
        humidities = humidities

    )

    fun getTestWeatherDto() = WeatherDto(
        weatherData = WeatherDataDto(
            time = time,
            temperatures = temperatures,
            weatherCodes = weatherCodes,
            pressure = pressure,
            windspeed = windspeed,
            humidities = humidities
        )
    )

    fun getLocationData() = LocationData(latitude = 4.5, longitude = 6.7)
}