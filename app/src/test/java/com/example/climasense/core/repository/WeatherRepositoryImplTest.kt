package com.example.climasense.core.repository

import com.example.climasense.core.enums.UnitSystem
import com.example.climasense.core.model.WeatherOneCallResponse
import com.skydoves.sandwich.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test


class WeatherRepositoryImplTest {
    private lateinit var mockEngine: MockEngine
    private lateinit var repositoryImpl: WeatherRepositoryImpl
    private val latitude = 37.7749
    private val longitude = -122.4194


    @Test
    fun `when API returns success, repository should return success`() = runBlocking {

        mockEngine = MockEngine { request ->

            assert(request.url.encodedPath.contains("onecall"))
            assert(request.url.parameters["lat"] == latitude.toString())
            assert(request.url.parameters["lon"] == longitude.toString())

            respond(
                content = """
                {
                  "lat": $latitude,
                  "lon": $longitude,
                  "current": {
                    "clouds": 10,
                    "dt": 1700000000,
                    "dew_point": 1.0,
                    "humidity": 0,
                    "uvi": 1.0,
                    "wind_deg": 0,
                    "pressure": 0,
                    "weather": [],
                    "wind_speed": 0.0
                  },
                  "hourly": [],
                  "daily": [],
                  "alerts": []
                }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        repositoryImpl = WeatherRepositoryImpl(httpClient)
        val result =
            repositoryImpl.getOneTimeResponse(latitude, longitude, unitSystem = UnitSystem.METRIC)
        println("Result type: ${result::class.simpleName}")
        println("Expected type: ApiResponse.Success")
        println("Result: $result")

        assertTrue(result is ApiResponse.Success<WeatherOneCallResponse>)
    }

    @Test
    fun `when API returns error, repository should return same`() = runBlocking {
        mockEngine = MockEngine { request ->
            respond(
                content = "Something went wrong",
                status = HttpStatusCode.InternalServerError
            )

        }
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        repositoryImpl = WeatherRepositoryImpl(httpClient)

        val result =
            repositoryImpl.getOneTimeResponse(latitude, longitude, unitSystem = UnitSystem.METRIC)

        assertTrue(result is ApiResponse.Failure.Error)
    }
}
