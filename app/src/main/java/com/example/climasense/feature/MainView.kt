package com.example.climasense.feature

import AlertRoute
import ForecastRoute
import HomeRoute
import SettingsRoute
import android.Manifest
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.climasense.core.theme.ClimaSenseTheme
import com.example.climasense.feature.alerts.AlertView
import com.example.climasense.feature.forecast.ForeCastView
import com.example.climasense.feature.home.HomeView
import com.example.climasense.feature.settings.SettingsView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainView(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    LaunchedEffect(locationPermissionState.status) {
        when {
            locationPermissionState.status.isGranted -> {
                viewModel.fetchWeather(
                )
            }

            locationPermissionState.status.shouldShowRationale -> {
//                // User denied before → show rationale
//                viewModel._state.value = UiState.PermissionDenied
            }

            else -> {
                locationPermissionState.launchPermissionRequest()
            }
        }
    }
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {
                MainMenu.appBottomMenu.mapIndexed { _, mainMenu ->
                    NavigationBarItem(
                        label = { Text(mainMenu.title) },
                        icon = { Icon(mainMenu.icon, contentDescription = null) },
                        selected = currentRoute == mainMenu.route,
                        onClick = {
                            navController.navigate(mainMenu.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }

        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> { HomeView(viewModel = viewModel) }
            composable<ForecastRoute> { ForeCastView(viewModel = viewModel) }
            composable<AlertRoute> { AlertView(viewModel = viewModel) }
            composable<SettingsRoute> { SettingsView() }
        }


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
    val icon: ImageVector,
    val route: Any


) {
    companion object {
        val appBottomMenu = listOf(
            MainMenu(
                title = "Home",
                icon = Icons.Outlined.Home,
                route = HomeRoute
            ),
            MainMenu(
                title = "Forecast",
                icon = Icons.Outlined.DateRange,
                route = ForecastRoute
            ),
            MainMenu(
                title = "Alerts",
                icon = Icons.Outlined.Notifications,
                route = AlertRoute
            ),
            MainMenu(
                title = "Settings",
                icon = Icons.Outlined.Settings,
                route = SettingsRoute
            ),
        )
    }
}

