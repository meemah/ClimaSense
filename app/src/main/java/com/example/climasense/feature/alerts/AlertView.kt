package com.example.climasense.feature.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.stylusHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.theme.customColors

@Composable
fun AlertView(modifier: Modifier = Modifier) {
   return Column(modifier) {
       Text("Active Alerts", style = MaterialTheme.typography.titleMedium)
       Spacer(Modifier.height(10.dp))
       Column(
           verticalArrangement = Arrangement.spacedBy(10.dp)
       ) {
           AlertCard(title = "Severe Thunderstorm", subTitle = "Until 4.30pm in your country",)
       }
   }
}

@Composable
private fun AlertCard(title: String, subTitle:String,) {
    Column (modifier = Modifier.customCard().fillMaxWidth()){
        Text(title,style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(5.dp))
        Text(subTitle, style = TextStyle(
            color = MaterialTheme.customColors.darkLightGrey
        ))
    }
}