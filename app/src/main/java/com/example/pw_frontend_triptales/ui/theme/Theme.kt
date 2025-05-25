package com.example.pw_frontend_triptales.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val LightColors = lightColorScheme(
    primary = InstaPrimary,
    onPrimary = Color.White,
    secondary = InstaGradientCenter,
    background = InstaBackgroundLight,
    onBackground = InstaTextLight,
    surface = InstaSurfaceLight,
    onSurface = InstaTextLight,
)

private val DarkColors = darkColorScheme(
    primary = InstaPrimary,
    onPrimary = Color.Black,
    secondary = InstaGradientCenter,
    background = InstaBackgroundDark,
    onBackground = InstaTextDark,
    surface = InstaSurfaceDark,
    onSurface = InstaTextDark,
)

@Composable
fun TripTalesTheme(
    useDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
