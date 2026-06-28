package com.example.climasense.core.enums


enum class UnitSystem(
    val apiValue: String,
    val temperatureSymbol: String,
    val windSpeedUnit: String,
    val visibilityUnit: String,
    private val visibilityFromMeters: (Int) -> Double
) {
    METRIC(
        apiValue = "metric",
        temperatureSymbol = "°C",
        windSpeedUnit = "m/s",
        visibilityUnit = "km",
        visibilityFromMeters = { meters -> meters / 1000.0 }
    ),
    IMPERIAL(
        apiValue = "imperial",
        temperatureSymbol = "°F",
        windSpeedUnit = "mph",
        visibilityUnit = "mi",
        visibilityFromMeters = { meters -> meters / 1609.0 }
    );
    
    fun formatVisibility(meters: Int): String =
        "%.1f %s".format(visibilityFromMeters(meters), visibilityUnit)

    fun formatWind(speed: Double?): String =
        speed?.let { "$it $windSpeedUnit" } ?: "--"

    fun formatTemp(temp: Double?): String =
        temp?.let { "${it.toInt()}$temperatureSymbol" } ?: "--"
}