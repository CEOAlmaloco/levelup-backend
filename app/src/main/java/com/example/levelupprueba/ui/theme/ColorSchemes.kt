package com.example.levelupprueba.ui.theme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = TextHigh,
    primaryContainer = BrandPrimaryDark,
    secondary = BrandSecondary,
    onSecondary = TextHigh,
    secondaryContainer = BrandSecondaryDark,
    background = Color(0xFFFDFDFD),   // fondo claro
    onBackground = Color(0xFF1C1C1C),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF565656),
    error = BrandError,
    onError = TextHigh,
    outline = NeutralDivider,
    surfaceVariant = NeutralSurface,
    scrim = Color(0x66000000) // negro translúcido
)

val DarkColorScheme = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = TextHigh,
    primaryContainer = BrandPrimaryDark,
    secondary = BrandSecondary,
    onSecondary = TextMedium,
    secondaryContainer = BrandSecondaryDark,
    background = NeutralDark,
    onBackground = TextHigh,
    surface = NeutralSurface,
    onSurface = TextMedium,
    error = BrandError,
    onError = TextHigh,
    outline = NeutralDivider,
    surfaceVariant = NeutralSurface,
    scrim = Color(0x66000000) // negro translúcido
)