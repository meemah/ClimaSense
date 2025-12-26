package com.example.climasense.core.enums
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class WeatherType( val icon: ImageVector){
    RAINY( Icons.Outlined.WaterDrop),
    CLOUD(Icons.Outlined.Cloud),
    SUNNY(Icons.Outlined.WbSunny),
    PARTLY_CLOUD(Icons.Outlined.WbCloudy),
    THUNDERSTORM(Icons.Outlined.Thunderstorm);
}

data class WeatherExample(
    val weatherType: WeatherType,
    val value: Int,
    val day: String,
    val time: String,
    val speed: Int,
    val perception: Int
){
    companion object{
        val mockData = listOf(
            WeatherExample(
                weatherType = WeatherType.CLOUD,
                value = 50,
                day = "Mon",
                time = "12pm",
                speed = 2,
                perception = 5
            ),
            WeatherExample(
                weatherType = WeatherType.RAINY,
                value = 10,
                day = "Tue",
                time = "1pm",
                speed = 30,
                perception = 80
            ),
            WeatherExample(
                weatherType = WeatherType.PARTLY_CLOUD,
                value = 20,
                day = "Wed",
                time = "2pm",
                speed = 15,
                perception = 50
            ),
            WeatherExample(
                weatherType = WeatherType.THUNDERSTORM,
                value = 50,
                day = "Thus",
                time = "3pm",
                speed = 2,
                perception = 5
            ),
            WeatherExample(
                weatherType = WeatherType.CLOUD,
                value = 50,
                day = "Fri",
                time = "4pm",
                speed = 2,
                perception = 5
            ),
            WeatherExample(
                weatherType = WeatherType.SUNNY,
                value = 30,
                day = "Sat",
                time = "5pm",
                speed = 2,
                perception = 5
            ),
            WeatherExample(
                weatherType = WeatherType.RAINY,
                value = 50,
                day = "Sun",
                time = "6pm",
                speed = 2,
                perception = 5
            ),

            )
    }
}
