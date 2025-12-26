package com.example.climasense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.climasense.core.theme.ClimaSenseTheme
import com.example.climasense.feature.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClimaSenseTheme(darkTheme = true, dynamicColor = false) {
                MainView()
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