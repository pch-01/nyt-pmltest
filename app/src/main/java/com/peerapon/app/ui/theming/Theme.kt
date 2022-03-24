package com.peerapon.app.ui.theming

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = Typography(),
        content = content
    )
}

private val LightThemeColors = lightColors(
    primary = Color.Red,
    primaryVariant = Color.Red,
    onPrimary = Color.White,
    secondary = Color.Red,
    secondaryVariant = Color.Red,
    onSecondary = Color.White,
    error = Color.Red,
    onBackground = Color.White
)