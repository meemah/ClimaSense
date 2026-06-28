package com.example.climasense.core.repository


import com.example.climasense.BuildConfig
import com.example.climasense.core.enums.UnitSystem
import com.example.climasense.core.model.WeatherOneCallResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import javax.inject.Inject


interface WeatherRepository {
    suspend fun getOneTimeResponse(
        latitude: Double,
        longitude: Double,
        unitSystem: UnitSystem
    ): ApiResponse<WeatherOneCallResponse>

}

class WeatherRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : WeatherRepository {
    override suspend fun getOneTimeResponse(
        latitude: Double,
        longitude: Double,
        unitSystem: UnitSystem
    ): ApiResponse<WeatherOneCallResponse> {
        return httpClient.getApiResponse<WeatherOneCallResponse>("onecall") {
            parameter("lat", latitude)
            parameter("lon", longitude)
            parameter("units", unitSystem.apiValue)
            parameter("appid", BuildConfig.WEATHER_API_KEY)
        }
    }

}