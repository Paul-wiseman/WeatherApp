package com.wiseman.wetherapp.data.repository

import arrow.core.Either
import com.wiseman.wetherapp.TestUtil
import com.wiseman.wetherapp.data.location.LocationTracker
import com.wiseman.wetherapp.data.mappers.toWeatherInfo
import com.wiseman.wetherapp.data.remote.WeatherApi
import com.wiseman.wetherapp.util.Failure
import io.mockk.checkUnnecessaryStub
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {
    private lateinit var weatherAppRepositoryImpl: WeatherRepositoryImpl
    private val mockWeatherApiService = mockk<WeatherApi>()
    private val mockLocationTracker = mockk<LocationTracker>()
    private val locationData = TestUtil.getLocationData()
    private val testWeatherData = TestUtil.getTestWeatherDto()

    @AfterEach
    fun tearDown() {
        confirmVerified(mockLocationTracker, mockWeatherApiService)
        checkUnnecessaryStub(mockLocationTracker, mockWeatherApiService)
    }

    @Test
    fun shouldReturnWeatherInfoForCorrectLocationData() = runTest {
        // given
        coEvery {
            mockWeatherApiService.getWeatherData(
                lat = locationData.latitude,
                long = locationData.longitude
            )
        } returns testWeatherData
        coEvery { mockLocationTracker.getCurrentLocation() } returns Either.Right(locationData)

        weatherAppRepositoryImpl = WeatherRepositoryImpl(
            mockWeatherApiService,
            mockLocationTracker,
            StandardTestDispatcher(testScheduler)
        )

        // when
        val weatherData = weatherAppRepositoryImpl.getWeatherData()

        advanceUntilIdle()

        // then

        assertEquals(Either.Right(testWeatherData.toWeatherInfo()), weatherData)

        coVerify(exactly = ONCE) {
            mockLocationTracker.getCurrentLocation()
            mockWeatherApiService.getWeatherData(locationData.latitude, locationData.longitude)
        }
    }

    @Test
    fun shouldReturnFailureWhenUserLocationIsUnAvailable() = runTest {
        // given
        val expected = Failure.LocationError()
        coEvery { mockLocationTracker.getCurrentLocation() } returns Either.Left(
            expected
        )

        weatherAppRepositoryImpl = WeatherRepositoryImpl(
            mockWeatherApiService,
            mockLocationTracker,
            StandardTestDispatcher(testScheduler)
        )

        // when
        val weatherData = weatherAppRepositoryImpl.getWeatherData()

        advanceUntilIdle()

        // when
        assertEquals(Either.Left(expected), weatherData)

        coVerify(exactly = ONCE) { mockLocationTracker.getCurrentLocation() }
    }

    @Test
    fun shouldReturnFailureWhenThereIsAnExceptionWithFetchingWeatherData() = runTest {
        // given
        val expected = "Something Went Wrong"
        coEvery { mockLocationTracker.getCurrentLocation() } returns Either.Right(
            locationData
        )
        coEvery {
            mockWeatherApiService.getWeatherData(
                locationData.latitude,
                locationData.longitude
            )
        } throws RuntimeException(expected)
        weatherAppRepositoryImpl = WeatherRepositoryImpl(
            mockWeatherApiService,
            mockLocationTracker,
            StandardTestDispatcher(testScheduler)
        )

        // when
        val weatherData = weatherAppRepositoryImpl.getWeatherData()

        advanceUntilIdle()

        // then
        assertEquals(Either.Left(Failure.NetworkError(expected)), weatherData)

        coVerify(exactly = ONCE) {
            mockLocationTracker.getCurrentLocation()
            mockWeatherApiService.getWeatherData(locationData.latitude, locationData.longitude)
        }
    }

    private companion object {
        const val ONCE = 1
    }
}