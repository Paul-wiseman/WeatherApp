package com.wiseman.wetherapp.presentation.viewmodel

import app.cash.turbine.test
import arrow.core.Either
import com.wiseman.wetherapp.TestUtil
import com.wiseman.wetherapp.data.mappers.toWeatherInfo
import com.wiseman.wetherapp.domain.repository.WeatherRepository
import com.wiseman.wetherapp.presentation.state.WeatherState
import com.wiseman.wetherapp.util.Failure
import com.wiseman.wetherapp.util.MainDispatcherRule
import io.mockk.checkUnnecessaryStub
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var mockWeatherRepository: WeatherRepository
    private val testWeatherData = TestUtil.getTestWeatherDto().toWeatherInfo()


    companion object {
        @JvmField
        @RegisterExtension
        val testDispatcherRule = MainDispatcherRule()
        const val ONCE = 1
    }


    @BeforeEach
    fun setUp() {
        mockWeatherRepository = mockk()
        weatherViewModel = WeatherViewModel(mockWeatherRepository)
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(mockWeatherRepository)
        checkUnnecessaryStub(mockWeatherRepository)
    }

    @Test
    fun shouldLoadWeatherData() = runTest {
        // given
        coEvery { mockWeatherRepository.getWeatherData() } returns flowOf(
            Either.Right(
                testWeatherData
            )
        )

        weatherViewModel.weatherDataUiState.test {

            //when
            weatherViewModel.loadWeatherInfo()

            // then
            assertEquals(WeatherState.Loading, awaitItem())
            assertEquals(
                WeatherState.Success(testWeatherData),
                awaitItem()
            )
            coVerify(exactly = ONCE) { mockWeatherRepository.getWeatherData() }
        }

    }

    @Test
    fun shouldReturnAnErrorWhenLoadingWeatherDataFails() = runTest {
        // given
        coEvery { mockWeatherRepository.getWeatherData() } returns flowOf(Either.Left(Failure.LocationError()))

        weatherViewModel.weatherDataUiState.test {
            //when
            weatherViewModel.loadWeatherInfo()

            //then
            coVerify(exactly = ONCE) { mockWeatherRepository.getWeatherData() }
            assertEquals(WeatherState.Loading, awaitItem())
            assertEquals(
                WeatherState.Error(Failure.LocationError()),
                awaitItem()
            )
        }

    }

    @Test
    fun shouldUpdateRefreshState() = runTest {
        // given
        coEvery { mockWeatherRepository.getWeatherData() } returns flowOf(
            Either.Right(
                testWeatherData
            )
        )

        weatherViewModel.isRefreshing.test {
            // when
            weatherViewModel.refreshWeatherData()

            // then
            assertEquals(false, awaitItem(), "first item emitted")
            assertEquals(true, awaitItem(), "second item emitted")
            assertEquals(false, awaitItem(), "third item emitted")
            coVerify(exactly = ONCE) { mockWeatherRepository.getWeatherData() }
        }

    }
}
