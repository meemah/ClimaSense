package com.example.climasense.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.climasense.core.datastore.ThemeMode
import com.example.climasense.core.theme.customColors


@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var isSelected by remember { mutableStateOf(false) }
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Dark Theme")
            Switch(
                checked = themeMode == ThemeMode.DARK,
                onCheckedChange = {
                    viewModel.setTheme(
                        if (it) ThemeMode.DARK else ThemeMode.LIGHT
                    )
                }, modifier = Modifier.size(20.dp),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF8BC34A),           // Thumb when ON
                    checkedTrackColor = Color(0xFF8BC34A).copy(alpha = 0.5F),
                    uncheckedTrackColor = Color.LightGray,
                    uncheckedThumbColor = MaterialTheme.customColors.zinc100andZinc800,
                )
            )

        }
    }
}