package com.example.pw_frontend_triptales.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PrimaryLight = Color(0xFF6200EE) // Viola acceso
private val PrimaryDark = Color(0xFFBB86FC)  // Viola chiaro

private val SecondaryLight = Color(0xFF03DAC6) // Verde acqua
private val SecondaryDark = Color(0xFF03DAC6)

private val BackgroundLight = Color(0xFFF5F5F5)
private val BackgroundDark = Color(0xFF121212)

private val SurfaceLight = Color(0xFFFFFFFF)
private val SurfaceDark = Color(0xFF1E1E1E)

private val ErrorLight = Color(0xFFB00020)
private val ErrorDark = Color(0xFFCF6679)

private val LightColors = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    secondary = SecondaryLight,
    onSecondary = Color.Black,
    background = BackgroundLight,
    onBackground = Color.Black,
    surface = SurfaceLight,
    onSurface = Color.Black,
    error = ErrorLight,
    onError = Color.White,
)

private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color.Black,
    secondary = SecondaryDark,
    onSecondary = Color.Black,
    background = BackgroundDark,
    onBackground = Color.White,
    surface = SurfaceDark,
    onSurface = Color.White,
    error = ErrorDark,
    onError = Color.Black,
)

@Composable
fun TripTalesTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
