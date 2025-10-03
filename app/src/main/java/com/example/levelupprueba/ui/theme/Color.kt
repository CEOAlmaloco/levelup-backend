package com.example.levelupprueba.ui.theme

import androidx.compose.ui.graphics.Color

// ----------- Primary Colors -----------
val Primary           = Color(0xFF222831) // Azul oscuro elegante, fondo principal
val PrimaryVariant    = Color(0xFF393E46) // Variante para superficies elevadas
val Secondary         = Color(0xFF00ADB5) // Azul vibrante, acentos y botones
val SecondaryVariant  = Color(0xFF007B8A) // Azul profundo para estados activos

// ----------- Background & Surface -----------
val Background        = Color(0xFF23272F) // Fondo general (muy oscuro, moderno)
val Surface           = Color(0xFF2C313A) // Cards, dialogs, bottom sheets
val Divider           = Color(0xFF393E46) // Líneas divisorias y bordes sutiles

// ----------- Accent Colors -----------
val AccentGreen       = Color(0xFF00C853) // Verde para confirmaciones, éxito
val AccentBlue        = Color(0xFF00ADB5) // Azul para info, enlaces
val AccentRed         = Color(0xFFD7263D) // Rojo intenso para error o peligro
val AccentYellow      = Color(0xFFFFD600) // Amarillo para advertencias

// ----------- Text Colors -----------
val TextPrimary       = Color(0xFFF8F8F8) // Blanco cálido para texto principal
val TextSecondary     = Color(0xFFB0BEC5) // Gris claro para texto secundario
val TextDisabled      = Color(0xFF757575) // Texto deshabilitado, hints

// ----------- Status Colors (MaterialTheme) -----------
val Error             = AccentRed
val Success           = AccentGreen
val Warning           = AccentYellow
val Info              = AccentBlue

// ----------- Button Colors -----------
val ButtonMenuBackground      = Divider         // Gris moderno para botones menú
val ButtonMenuContent         = TextSecondary   // Texto claro en botones menú
val ButtonLevelUpGradientStart = AccentGreen    // Degradado LevelUp verde
val ButtonLevelUpGradientEnd   = AccentBlue     // Degradado LevelUp azul