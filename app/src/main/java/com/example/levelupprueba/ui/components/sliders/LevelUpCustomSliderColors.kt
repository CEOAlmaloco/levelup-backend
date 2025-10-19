package com.example.levelupprueba.ui.components.sliders

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import com.example.levelupprueba.ui.theme.SemanticColors

@Composable
fun levelUpCustomSliderColors(): SliderColors = SliderDefaults.colors(
    thumbColor = SemanticColors.AccentBlue,

    activeTrackColor = SemanticColors.AccentGreen,
    activeTickColor = MaterialTheme.colorScheme.background,

    inactiveTrackColor = MaterialTheme.colorScheme.outline,
    inactiveTickColor = MaterialTheme.colorScheme.background,

    disabledActiveTickColor = MaterialTheme.colorScheme.background,
    disabledInactiveTickColor = MaterialTheme.colorScheme.background,
)