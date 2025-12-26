package com.example.climasense.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climasense.core.enums.WeatherExample
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.theme.customColors


@Composable
fun HomeView(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize(),
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier.padding(bottom = 10.dp,)) {
            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
            Text("San Francisco, CA",
                modifier = Modifier.padding(start = 10.dp,),
                style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
            ))
        }

        Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Thunderstorm,
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
                Text("72°",
                    modifier= Modifier.padding(vertical = 10.dp),
                    style =MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 80.sp,
                ))
                Text("Partly Cloudy")
                Text("H:75°L58°",
                    modifier = Modifier.padding(top = 5.dp, bottom = 15.dp ),
                    style = TextStyle(
                    color = MaterialTheme.customColors.darkLightGrey
                ))

            }
        }
        Row(
            modifier= Modifier
                .customCard()
                .fillMaxWidth()
        ) {
            Icon(Icons.Default.Air, contentDescription = null)
            Text(text = "Air Quality",modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.background(
                    color = Color.Green,
                    shape = RoundedCornerShape(10.dp)
                )
            ){
                Text("Good")
            }

        }
        Column(modifier= Modifier
            .customCard()
            .fillMaxWidth()) {
            Text("7 DAY FORECAST", style = TextStyle(
                color = MaterialTheme.customColors.darkLightGrey
            ), modifier = Modifier.padding(bottom = 10.dp))
            LazyRow (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
               items(WeatherExample.mockData){ weather ->
                   Column {
                       Text(weather.day.uppercase(), style = TextStyle(
                           fontWeight = FontWeight.Bold,
                           color = MaterialTheme.customColors.darkLightGrey
                       )
                       )
                       Icon(weather.weatherType.icon, contentDescription = null,modifier = Modifier
                           .padding(
                               vertical = 10.dp
                           )
                           .size(30.dp)
                          )
                       Text("${weather.value}°")
                   }

               }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 15.dp)
        )
        {
            item {
                AirQualityCard(title = "Humidity", value = "75%", icon = Icons.Default.WaterDrop)
            }
            item {
                AirQualityCard(title = "Wind", value = "10 mph", icon = Icons.Default.Air)
            }
            item {
                AirQualityCard(title = "UV Index", value = "5", icon = Icons.Default.WbSunny)
            }
            item {
                AirQualityCard(title = "Visibility", value = "8 mi", icon = Icons.Default.Visibility)
            }
        }
    }
}

@Composable
private fun AirQualityCard(
    title:String,
    value:String,
    icon: ImageVector
) {

    Column (modifier = Modifier.customCard()){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 10.dp)){
            Icon(icon, contentDescription = null,
                tint = MaterialTheme.customColors.darkLightGrey,
                modifier = Modifier
                .size(20.dp))
            Text(title,
                modifier = Modifier.padding(start = 6.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.customColors.darkLightGrey, fontSize = 12.sp
            ))

        }
             Text(value, style = MaterialTheme.typography.titleMedium)
         }

}
