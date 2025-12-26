package com.example.climasense.feature

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings


import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climasense.core.theme.ClimaSenseTheme
import com.example.climasense.feature.airquality.AirQualityView
import com.example.climasense.feature.alerts.AlertView
import com.example.climasense.feature.forecast.ForeCastView
import com.example.climasense.feature.home.HomeView

@Composable
fun MainView() {
    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.surface, // needs a color for shadow
                tonalElevation = 4.dp
            ){
                MainMenu.appBottomMenu.mapIndexed { _ , mainMenu -> NavigationBarItem(
                    label = { Text(mainMenu.title)},
                    icon = { Icon(mainMenu.icon, contentDescription = null)},
                    selected = true,
                    onClick = {},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent
                    )
                )}
            }

        },
        modifier = Modifier.fillMaxSize() ) { innerPadding ->

        AirQualityView(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 20.dp, vertical = 20.dp)
        )

    }
}

@Preview
@Composable
fun MainViewPreview(modifier: Modifier = Modifier) {
    ClimaSenseTheme {
        MainView()
    }
}


data class MainMenu(
   val title: String,
 val icon : ImageVector


){
    companion object {
        val appBottomMenu = listOf(
            MainMenu(
                title = "Home",
                icon = Icons.Outlined.Home
            ),
            MainMenu(
                title = "Forecast",
                icon = Icons.Outlined.DateRange
            ),
            MainMenu(
                title = "Alerts",
                icon = Icons.Outlined.Notifications
            ),
            MainMenu(
                title = "Settings",
                icon = Icons.Outlined.Settings
            ),
        )
    }
}

