package com.example.levelupprueba.ui.theme

import android.os.Build

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextPrimary,
    primaryContainer = PrimaryVariant,
    secondary = Secondary,
    onSecondary = TextSecondary,
    secondaryContainer = SecondaryVariant,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    error = Error,
    onError = TextPrimary,
    outline = Divider,
    surfaceVariant = Surface,
    scrim = Divider
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = TextPrimary,
    primaryContainer = PrimaryVariant,
    secondary = Secondary,
    onSecondary = TextSecondary,
    secondaryContainer = SecondaryVariant,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    error = Error,
    onError = TextPrimary,
    outline = Divider,
    surfaceVariant = Surface,
    scrim = Divider
)

// CompositionLocal para proveer Dimens a toda la app
val LocalDimens = staticCompositionLocalOf<Dimens> {
    error("No Dimens Provided")
}

/**
 * Theme principal de la app, que inyecta colores, tipografía y dimensiones adaptativas.
 *
 * @param darkTheme Si se debe usar el tema oscuro (por defecto, según el sistema)
 * @param dynamicColor Si se deben usar colores dinámicos (Android 12+)
 * @param windowSizeClass Clase de tamaño de ventana (compact, medium, expanded)
 * @param content Composable raíz
 */
@Composable
fun LevelUpPruebaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    windowSizeClass: WindowWidthSizeClass,
    content: @Composable () -> Unit
) {
    // Selecciona dimensiones según el tipo de pantalla (responsive)
    val dimens: Dimens = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> compactDimens
        WindowWidthSizeClass.Medium -> mediumDimens
        WindowWidthSizeClass.Expanded -> expandedDimens
        else -> mediumDimens
    }

    CompositionLocalProvider(LocalDimens provides dimens) {
        // Selección de paleta de colores según tema y versión de Android
        val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}