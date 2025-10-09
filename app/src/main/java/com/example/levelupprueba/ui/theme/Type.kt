package com.example.levelupprueba.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    // TITULO PRINCIPAL GRANDE
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,      // Título grande
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // TITULO MEDIANO
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,      // Título mediano
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    // TITULO PEQUEÑO
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,      // Título pequeño
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    ),
    // BODY GRANDE (texto principal)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,      // Texto normal
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // BODY MEDIO
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,      // Texto secundario
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    // BODY PEQUEÑO
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,      // Texto pequeño
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    // LABEL GRANDE (botones, chips)
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,      // Label grande
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    // LABEL MEDIO
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,      // Label mediano
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    // LABEL PEQUEÑO
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,      // Label pequeño
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)