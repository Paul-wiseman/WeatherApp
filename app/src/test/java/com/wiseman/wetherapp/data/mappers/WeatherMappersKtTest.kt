package com.wiseman.wetherapp.data.mappers

import com.wiseman.wetherapp.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WeatherMappersTest{

    @Test
    fun shouldMapFromWeatherDataDtoTWeatherDataMap(){
        //give
        val weatherDataDto = TestUtil.getWeatherDataDto()

        // when
        val actual = weatherDataDto.toWeatherDataMap()

        // then
        actual[0]?.forEachIndexed { index, weatherData ->
           assertEquals(weatherData.time.toString(), weatherDataDto.time[index])
           assertEquals(weatherData.pressure, weatherDataDto.pressure[index])
           assertEquals(weatherData.windSpeed, weatherDataDto.windspeed[index])
           assertEquals(weatherData.humidity, weatherDataDto.humidities[index])
           assertEquals(weatherData.temperature, weatherDataDto.temperatures[index])
        }
    }

    @Test
    fun shouldMapFromWeatherDtoToWeatherInfo(){
        // given
        val weatherDto = TestUtil.getTestWeatherDto()

        // when
        val actual = weatherDto.toWeatherInfo()

        assertEquals(weatherDto.toWeatherInfo().currentWeatherData?.time, actual.currentWeatherData?.time)
    }
}