package com.example.levelupprueba.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.example.levelupprueba.ui.theme.NeutralDivider
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.ui.theme.TextDisabled

@Composable
fun levelUpTextFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    disabledTextColor = TextDisabled,
    errorTextColor = SemanticColors.Error,

    focusedBorderColor = SemanticColors.AccentBlue,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    errorBorderColor = SemanticColors.Error,


    focusedLabelColor = SemanticColors.AccentBlue,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
    errorLabelColor = SemanticColors.Error,

    cursorColor = SemanticColors.AccentBlue,
    errorCursorColor = SemanticColors.Error,

)