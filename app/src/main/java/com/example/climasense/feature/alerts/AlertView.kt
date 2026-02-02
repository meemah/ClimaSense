package com.example.climasense.feature.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.climasense.core.modifiers.customCard
import com.example.climasense.core.states.EmptyState
import com.example.climasense.core.states.ErrorState
import com.example.climasense.core.states.LoadingState
import com.example.climasense.core.theme.customColors
import com.example.climasense.feature.MainViewModel
import com.example.climasense.feature.UiState

@Composable
fun AlertView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel

) {

    val alertState by viewModel.state.collectAsStateWithLifecycle()
    when (alertState) {
        is UiState.Loading -> {
            LoadingState(message = "Loading Alerts")
        }

        is UiState.Success -> {
            val alerts = (alertState as UiState.Success).data.alerts

            Column(modifier = modifier.padding(20.dp)) {
                Text("Active Alerts", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(10.dp))
                if (alerts.isEmpty()) {
                    EmptyState()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(alerts) {
                            AlertCard(title = it.event, subTitle = it.description)
                        }
                    }
                }

            }
        }

        is UiState.Error -> {
            ErrorState(message = (alertState as UiState.Error).message)
        }
    }

}

@Composable
private fun AlertCard(title: String, subTitle: String) {
    Column(
        modifier = Modifier
            .customCard()
            .fillMaxWidth()
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(5.dp))
        Text(
            subTitle, style = TextStyle(
                color = MaterialTheme.customColors.darkLightGrey
            )
        )
    }
}