package com.example.climasense.feature.airquality

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.theme.customColors


@Composable
fun AirQualityView(modifier: Modifier = Modifier) {
    Column(modifier) {
        Column(modifier = Modifier.customCard().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Box (contentAlignment = Alignment.Center,

                ){
                CircularProgressIndicator(
                    progress = { 0.5f },
                    color = Color.Green,
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.size(150.dp),
                    trackColor = MaterialTheme.colorScheme.background
                )
                Text("128",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 30.sp
                    ))

            }
            Spacer(Modifier.height(10.dp))
            Text("Healthy for sensitive groups", style = TextStyle(
                color = Color.Green
            ))
        }
        Spacer(Modifier.height(15.dp))
        Column(modifier = Modifier.customCard().fillMaxWidth()) {
            Text("Pollutant Breakdown", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(10.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf(
                      mapOf(
                         "acronym" to  "CO",
                          "name" to "Carbon Monoxide",
                          "value" to "0.9 ppm"
                      ),
                    mapOf("acronym" to  "0",
                    "name" to "Ozone",
                    "value" to "35.2 ppb"
                     )
                ).forEach {  item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(item["acronym"].toString(), modifier = Modifier

                            .customCard(
                            backgroundColor = MaterialTheme.customColors.zinc100andZinc800,
                            borderRadius = 8.0).size(25.dp).
                            wrapContentSize(Alignment.Center)      ,
                            style =  MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold,
                        ))
                        Text(item["name"].toString(),modifier = Modifier.padding(horizontal =  10.dp).weight(1f), style =
                        MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.customColors.darkLightGrey, fontWeight = FontWeight.Bold))
                        Text(item["value"].toString())
                    }


                }
            }
        }

    }
    
}

