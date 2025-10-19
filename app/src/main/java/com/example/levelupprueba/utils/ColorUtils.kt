package com.example.levelupprueba.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

@Composable
fun generateSoftColor(baseColor: Color, intensity: Float = 0.25f): Color {
    val background = MaterialTheme.colorScheme.surface
    return lerp(baseColor, background, intensity)
}