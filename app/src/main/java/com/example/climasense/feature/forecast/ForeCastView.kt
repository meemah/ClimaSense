package com.example.climasense.feature.forecast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.climasense.core.enums.WeatherExample
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.theme.customColors

@Composable
fun ForeCastView(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text("Today, October 2026", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            WeatherExample.mockData.forEach {  weather ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .customCard()) {
                    Icon(weather.weatherType.icon, contentDescription = null,modifier = Modifier.customCard(
                        backgroundColor =  MaterialTheme.customColors.zinc200andZinc900
                    ),)
                    Spacer(Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(weather.time, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            AirQualityIcon(
                                icon = Icons.Default.Air,
                                value = "4 mph"
                            )
                            AirQualityIcon(
                                icon = Icons.Default.WaterDrop,
                                value = "0%"
                            )
                        }

                    }
                    Text("${weather.value}°",style = MaterialTheme.typography.titleSmall, modifier = Modifier.align(
                        Alignment.CenterVertically
                    ))
                }
            }
        }
    }

}

@Composable
fun AirQualityIcon(icon: ImageVector, value: String) {
    Row {
        Icon(icon,contentDescription = null, tint = MaterialTheme.customColors.darkLightGrey, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(2.dp),)
        Text(value, style = TextStyle(
            color = MaterialTheme.customColors.darkLightGrey
        ))
    }
}