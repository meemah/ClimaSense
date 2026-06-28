package com.example.climasense.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climasense.core.LocationUtil
import com.example.climasense.core.enums.UnitSystem
import com.example.climasense.core.model.WeatherOneCallResponse
import com.example.climasense.core.repository.WeatherRepository
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationUtil: LocationUtil
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<WeatherOneCallResponse>>(UiState.Loading)
    val state: StateFlow<UiState<WeatherOneCallResponse>> = _state

    private val _locationName = MutableStateFlow<String?>(null)
    val locationName: StateFlow<String?> = _locationName
    private val _unitSystem = MutableStateFlow(UnitSystem.METRIC)
    val unitSystem: StateFlow<UnitSystem> = _unitSystem
    fun fetchWeather() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            if (!locationUtil.isLocationEnabled()) {
                _state.value =
                    UiState.Error("Location is turned off. Turn it on to see the weather.")
                return@launch
            }

            val location = locationUtil.getUserLocation()
            if (location == null) {
                _state.value = UiState.Error(message = "Oops we couldn't get your location")
            } else {
                _locationName.value =
                    locationUtil.getLocationName(location.latitude, location.longitude)
                weatherRepository.getOneTimeResponse(
                    longitude = location.longitude,
                    latitude = location.latitude,
                    unitSystem = _unitSystem.value
                ).onSuccess {
                    _state.value = UiState.Success(data)
                }.onFailure {
                    _state.value = UiState.Error(message = message())
                }
            }

        }
    }
}


sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}