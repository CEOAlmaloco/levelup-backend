package com.example.levelupprueba.ui.components.switches

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import com.example.levelupprueba.ui.theme.BrandPrimary
import com.example.levelupprueba.ui.theme.NeutralDivider
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.ui.theme.TextDisabled
import com.example.levelupprueba.ui.theme.TextHigh

@Composable
fun levelUpSwitchColors() = SwitchDefaults.colors(
    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,               // Circulito cuando el switch está activado
    checkedTrackColor = SemanticColors.AccentGreen,           // Fondo cuando el switch está activado

    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,             // Circulito cuando está desactivado
    uncheckedTrackColor = MaterialTheme.colorScheme.outline,         // Fondo cuando está desactivado

    disabledCheckedThumbColor = TextDisabled,   // Circulito cuando está deshabilitado y activado
    disabledCheckedTrackColor = BrandPrimary,   // Fondo cuando está deshabilitado y activado

    disabledUncheckedThumbColor = TextDisabled, // Circulito cuando está deshabilitado y desactivado
    disabledUncheckedTrackColor = TextDisabled  // Fondo cuando está deshabilitado y desactivado
)