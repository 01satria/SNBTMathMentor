package com.snbt.mathmentor.presentation.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Primary: Deep Blue (UTBK vibe)
private val primaryLight = Color(0xFF1565C0)
private val onPrimaryLight = Color.White
private val primaryContainerLight = Color(0xFFD6E4FF)
private val secondaryLight = Color(0xFF43A047)
private val tertiaryLight = Color(0xFFF57C00)
private val backgroundLight = Color(0xFFF8F9FF)
private val surfaceLight = Color.White
private val errorLight = Color(0xFFD32F2F)

private val primaryDark = Color(0xFF90CAF9)
private val onPrimaryDark = Color(0xFF001A3D)
private val primaryContainerDark = Color(0xFF003F8A)
private val secondaryDark = Color(0xFFA5D6A7)
private val tertiaryDark = Color(0xFFFFCC80)
private val backgroundDark = Color(0xFF0D1117)
private val surfaceDark = Color(0xFF161B22)
private val errorDark = Color(0xFFEF9A9A)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    secondary = secondaryLight,
    tertiary = tertiaryLight,
    background = backgroundLight,
    surface = surfaceLight,
    error = errorLight
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark,
    background = backgroundDark,
    surface = surfaceDark,
    error = errorDark
)

@Composable
fun SNBTMathMentorTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(colorScheme = colors, typography = Typography(), content = content)
}
