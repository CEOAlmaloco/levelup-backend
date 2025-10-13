package com.example.levelupprueba.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.levelupprueba.ui.theme.Dimens

/**
 * Text Field personalizado estándar para formularios
 *
 * Este botón aplica gradiente y estilos de manera consistente con la paleta caracteristica de LevelUp
 *
 * @param value Valor actual del campo de texto
 * @param onValueChange Callback para actualizar el valor del campo al escribir
 * @param label Etiqueta del campo de texto
 * @param enabled Indica si el campo de texto está habilitado o no
 * @param isError Muestra un indicador de error en el campo
 * @param isSuccess Muestra un indicador de exito en el campo
 * @param supportingText Mensaje de ayuda o error debajo del campo
 * @param singleLine Indica si el campo solo puede tener una linea
 * @param readOnly Indica si el campo solo puede ser leído
 * @param trailingIcon Icono que aparece al lado derecho del campo
 * @param placeholder Texto que se muestra cuando el campo está vacío
 * @param modifier Modificador para personalizar el campo
 * @param keyboardOptions Opciones de teclado para el campo
 * @param dimens Dimensiones del campo

 */
@Composable
fun LevelUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    supportingText: (@Composable (() -> Unit))? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    dimens: Dimens
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = levelUpOutlinedTextFieldColors(isSuccess = isSuccess),
        label = { Text(text = label, fontSize = dimens.bodySize) },
        enabled = enabled,
        isError = isError,
        supportingText = supportingText,
        singleLine = singleLine,
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        placeholder = placeholder,
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}