package com.example.climasense.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.climasense.core.model.Weather
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.states.EmptyState
import com.example.climasense.core.states.ErrorState
import com.example.climasense.core.states.LoadingState
import com.example.climasense.core.theme.customColors
import com.example.climasense.feature.MainViewModel
import com.example.climasense.feature.UiState
import java.time.format.DateTimeFormatter


@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val weatherState by viewModel.state.collectAsStateWithLifecycle()
    val locationName by viewModel.locationName.collectAsStateWithLifecycle()
    when (weatherState) {
        is UiState.Loading -> {
            LoadingState()
        }

        is UiState.Success -> {
            val data = (weatherState as UiState.Success).data
            val currentWeather: Weather? = data.current.weather.firstOrNull()
            if (currentWeather == null) {
                EmptyState()
            } else {
                Column(
                    modifier = modifier.fillMaxSize()

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left,
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            locationName ?: "Locating....",
                            modifier = Modifier.padding(start = 10.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                            )
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                "https://openweathermap.org/img/wn/${currentWeather.icon}.png",
                                contentDescription = currentWeather.description,
                                modifier = Modifier.size(130.dp)
                            )

                            Text(
                                "${data.current.temp?.day}°",
                                modifier = Modifier.padding(vertical = 10.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 80.sp,
                                )
                            )
                            Text(currentWeather.description)
                            Text(
                                "H:75°L58°",
                                modifier = Modifier.padding(top = 5.dp, bottom = 15.dp),
                                style = TextStyle(
                                    color = MaterialTheme.customColors.darkLightGrey
                                )
                            )

                        }
                    }
                    Column(
                        modifier = Modifier
                            .customCard()
                            .fillMaxWidth()
                    ) {
                        Text(
                            "7 DAY FORECAST", style = TextStyle(
                                color = MaterialTheme.customColors.darkLightGrey
                            ), modifier = Modifier.padding(bottom = 10.dp)
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items(data.daily) { weather ->
                                Column {
                                    Text(
                                        DateTimeFormatter.ofPattern("EEE").format(weather.dt)
                                            .uppercase(), style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.customColors.darkLightGrey
                                        )
                                    )
                                    AsyncImage(
                                        "https://openweathermap.org/img/wn/${weather.weather.first().icon}.png",
                                        contentDescription = weather.weather.first().description,
                                        modifier = Modifier
                                            .padding(
                                                vertical = 10.dp
                                            )
                                            .size(30.dp)
                                    )
                                    Text("${weather.temp?.day}°")
                                }

                            }
                        }
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(vertical = 15.dp)
                    ) {
                        item {
                            AirQualityCard(
                                title = "Humidity",
                                value = "${data.current.humidity}%",
                                icon = Icons.Default.WaterDrop
                            )
                        }
                        item {
                            AirQualityCard(
                                title = "Wind",
                                value = "${data.current.windSpeed} mph",
                                icon = Icons.Default.Air
                            )
                        }
                        item {
                            AirQualityCard(
                                title = "UV Index",
                                value = "${data.current.uvi}",
                                icon = Icons.Default.WbSunny
                            )
                        }
                        item {
                            AirQualityCard(
                                title = "Visibility",
                                value = "${data.current.visibility} mi",
                                icon = Icons.Default.Visibility
                            )
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            val message = (weatherState as UiState.Error).message
            ErrorState(message = message, onRetry = { viewModel.fetchWeather() })
        }
    }

}

@Composable
private fun AirQualityCard(
    title: String, value: String, icon: ImageVector
) {

    Column(modifier = Modifier.customCard(borderRadius = 10.0)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.customColors.darkLightGrey,
                modifier = Modifier.size(20.dp)
            )
            Text(
                title,
                modifier = Modifier.padding(start = 6.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.customColors.darkLightGrey, fontSize = 12.sp
                )
            )

        }
        Text(value, style = MaterialTheme.typography.titleMedium)
    }

}
