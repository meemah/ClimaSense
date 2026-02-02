package com.example.climasense.feature.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.theme.customColors
import com.example.climasense.feature.MainViewModel
import com.example.climasense.feature.UiState
import java.time.format.DateTimeFormatter

@Composable
fun ForeCastView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val weatherState by viewModel.state.collectAsStateWithLifecycle()
    when (weatherState) {
        is UiState.Error -> {

        }

        is UiState.Loading -> {

        }

        is UiState.Success -> {
            val data = ((weatherState as UiState.Success).data)
            Column(modifier) {
                Text("Today, October 2026", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(10.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(data.hourly) { weather ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .customCard()
                        ) {
//                            Icon(
//                                weather.weatherType.icon, contentDescription = null,
//                                modifier = Modifier.customCard(
//                                    backgroundColor = MaterialTheme.customColors.zinc200andZinc900
//                                ),
//                            )
                            Spacer(Modifier.width(10.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    DateTimeFormatter.ofPattern("h a").format(weather.dt),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(Modifier.height(6.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    AirQualityIcon(
                                        icon = Icons.Default.Air,
                                        value = "${weather.windSpeed} mph"
                                    )
                                    AirQualityIcon(
                                        icon = Icons.Default.WaterDrop,
                                        value = "${weather.humidity}%"
                                    )
                                }

                            }
                            Text(
                                "${weather.temp?.day}°",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.align(
                                    Alignment.CenterVertically
                                )
                            )
                        }

                    }

                }

            }
        }
    }

}

@Composable
fun AirQualityIcon(icon: ImageVector, value: String) {
    Row {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.customColors.darkLightGrey,
            modifier = Modifier.size(14.dp)
        )
        Spacer(Modifier.width(2.dp))
        Text(
            value, style = TextStyle(
                color = MaterialTheme.customColors.darkLightGrey
            )
        )
    }
}