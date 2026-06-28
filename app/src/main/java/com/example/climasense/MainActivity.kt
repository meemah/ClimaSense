package com.example.climasense


import MainRoute
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.climasense.core.datastore.ThemeMode
import com.example.climasense.core.theme.ClimaSenseTheme
import com.example.climasense.feature.MainView
import com.example.climasense.feature.MainViewModel
import com.example.climasense.feature.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: SettingsViewModel = hiltViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()

            val isDarkTheme = when (themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            ClimaSenseTheme(darkTheme = isDarkTheme, dynamicColor = false) {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = MainRoute,
                ) {
                    composable<MainRoute> {
                        hiltViewModel<MainViewModel>()
                        MainView()
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClimaSenseTheme {

    }
}

