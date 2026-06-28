package com.example.climasense.feature

import android.location.Location
import com.example.climasense.core.LocationUtil
import com.example.climasense.core.model.WeatherOneCallResponse
import com.example.climasense.core.repository.WeatherRepository
import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var locationUtil: LocationUtil
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        weatherRepository = mockk<WeatherRepository>()
        locationUtil = mockk<LocationUtil>()

        viewModel = MainViewModel(weatherRepository, locationUtil)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `initial state is loading`() {
        assertTrue(viewModel.state.value is UiState.Loading)
    }

    @Test
    fun `when location is null, return state is Error`() = runTest {
        coEvery { locationUtil.getUserLocation() } returns null
        viewModel.fetchWeather()
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.state.value
        assertTrue(state is UiState.Error)
    }

    @Test
    fun `when location is not null and repository returns success, return success`() = runTest {
        val mockLocation = mockk<Location>()

        every { mockLocation.latitude } returns 10.0
        every { mockLocation.longitude } returns 20.0
        val mockWeatherResponse = mockk<WeatherOneCallResponse>()
        coEvery { locationUtil.getUserLocation() } returns mockLocation
        coEvery {
            weatherRepository.getOneTimeResponse(
                any(),
                any(),
                any()
            )
        } returns ApiResponse.Success(mockWeatherResponse)

        viewModel.fetchWeather()
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.state.value
        assert(state is UiState.Success)
    }

    @Test
    fun `when location is not null and repository returns failed, return Failure`() = runTest {
        val mockLocation = mockk<Location>()

        every { mockLocation.latitude } returns 10.0
        every { mockLocation.longitude } returns 20.0

        coEvery { locationUtil.getUserLocation() } returns mockLocation
        coEvery {
            weatherRepository.getOneTimeResponse(
                any(),
                any(),
                any()
            )
        } returns ApiResponse.Failure.Error("Network failed")

        viewModel.fetchWeather()
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.state.value
        assert(state is UiState.Error)
    }


}
