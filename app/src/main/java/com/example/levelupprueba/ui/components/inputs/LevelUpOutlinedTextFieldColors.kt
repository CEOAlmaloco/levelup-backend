package com.example.levelupprueba.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.ui.theme.TextDisabled

/**
 * Colores personalizados para el campo de texto de LevelUp
 *
 * @param isSuccess Indica si el campo de texto está en estado de éxito
 */
@Composable
fun levelUpOutlinedTextFieldColors(isSuccess: Boolean = false): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    disabledTextColor = TextDisabled,
    errorTextColor = SemanticColors.Error,

    focusedBorderColor = SemanticColors.AccentBlue,
    unfocusedBorderColor = if (isSuccess) SemanticColors.Success else MaterialTheme.colorScheme.outline,
    errorBorderColor = SemanticColors.Error,


    focusedLabelColor = SemanticColors.AccentBlue,
    unfocusedLabelColor = if (isSuccess) SemanticColors.Success else MaterialTheme.colorScheme.onSurface,
    errorLabelColor = SemanticColors.Error,

    cursorColor = SemanticColors.AccentBlue,
    errorCursorColor = SemanticColors.Error,
)