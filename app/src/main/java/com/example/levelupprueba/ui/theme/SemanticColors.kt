package com.example.levelupprueba.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.example.levelupprueba.utils.generateSoftColor

object SemanticColors {
    // Estados
    val Success: Color      = BrandSuccess
    val Error: Color        = BrandError
    val Warning: Color      = BrandWarning
    val Info: Color         = BrandInfo

    // Acentos
    val AccentGreen: Color  = BrandSuccess
    val AccentBlue: Color   = BrandInfo
    val AccentRed: Color    = BrandError
    val AccentYellow: Color = BrandWarning

    // Acentos suaves
    val AccentGreenSoft: Color
        @Composable get() = generateSoftColor(AccentGreen)

    val AccentBlueSoft: Color
        @Composable get() = generateSoftColor(AccentBlue)

    val AccentRedSoft: Color
        @Composable get() = generateSoftColor(AccentRed)

    val AccentYellowSoft: Color
        @Composable get() = generateSoftColor(AccentYellow)
}

