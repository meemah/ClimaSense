package com.example.climasense.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Primary = Color(0xff279df1)
val BackgroundLight = Color(0xffFFFDF8)
val BackgroundDark = Color(0xff101b22)
val White = Color(0xffffffff)
val Grey = Color(0xff91b2ca)
val DarkSurface = Color(0xff192834)
val Zinc100 = Color(0xFFF4F4F5)
val Zinc200 = Color(0xFFE4E4E7)
val Zinc800 = Color(0xFF27272A)
val Zinc900 = Color(0xFF18181B)

data class CustomThemeColor(
    val darkLightGrey: Color,
    val zinc100andZinc800: Color,
    val zinc200andZinc900: Color
)

val DarkCustomThemeColor = CustomThemeColor(
    darkLightGrey = Grey,
    zinc100andZinc800 = Zinc800,
    zinc200andZinc900 = Zinc900

)

val LightCustomThemeColor = CustomThemeColor(
    darkLightGrey = Color.DarkGray,
    zinc100andZinc800 = Zinc100,
    zinc200andZinc900 = Zinc200

)

val LocalCustomColors = compositionLocalOf { LightCustomThemeColor }

val MaterialTheme.customColors: CustomThemeColor
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current