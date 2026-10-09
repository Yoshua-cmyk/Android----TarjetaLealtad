package com.example.miclienteleal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime

enum class ThemeMode {
    //Temas Manuales
    LIGHT, //Modo claro
    DARK, //Modo oscuro
    SUNSET, //Modo ocaso
    //Temas Automaticos
    SYSTEM, //Sigue el tema de Android
    DYNAMIC //Aplica los temas segun la hora del día
}

//Paleta para el Ocaso
private val OcasoPrimary = Color(0xFFE65100)
private val OcasoSecondary = Color(0xFF8E24AA)
private val OcasoTertiary = Color(0xFFFFB74D)
private val OcasoBackground = Color(0xFFEAE0D0)
private val OcasoSurface = Color(0xFFE8CA9E)
private val OcasoOnBackground = Color(0xFFF5E6EC)
private val OcasoOnSurface = Color(0xFF382C3B)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5)
)
private val OcasoColorScheme = lightColorScheme(
    primary = OcasoPrimary,
    secondary = OcasoSecondary,
    tertiary = OcasoTertiary,
    background = OcasoBackground,
    surface = OcasoSurface,
    onBackground = OcasoOnBackground,
    onSurface = OcasoOnSurface
)
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

fun getThemeByTime(): ThemeMode {
    val currentHour = LocalTime.now().hour
    return when (currentHour) {
        in 6..17 -> ThemeMode.LIGHT
        in 18..19 -> ThemeMode.SUNSET
        else -> ThemeMode.DARK
    }
}

@Composable
fun MiClienteLealTheme (
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val effectiveTheme = when (themeMode) {
        ThemeMode.SYSTEM -> {
            if (isSystemInDarkTheme()) ThemeMode.DARK else ThemeMode.LIGHT
        }
        ThemeMode.DYNAMIC -> getThemeByTime()
        else -> themeMode
    }

    val colorScheme = when (effectiveTheme) {
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.SUNSET -> OcasoColorScheme
        ThemeMode.DARK -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}