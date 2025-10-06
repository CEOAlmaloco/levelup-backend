package com.example.levelupprueba.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Text Field personalizado estándar para formularios.
 *
 * Este botón aplica gradiente y estilos de manera consistente con la paleta caracteristica de LevelUp.
 *
 */
@Composable
fun LevelUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    supportingText: (@Composable (() -> Unit))? = null,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = levelUpTextFieldColors(isSuccess = isSuccess),
        label = { Text(label) },
        isError = isError,
        supportingText = supportingText,
        singleLine = singleLine,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}