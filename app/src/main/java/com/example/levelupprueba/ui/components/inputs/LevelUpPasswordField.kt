package com.example.levelupprueba.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.levelupprueba.ui.theme.Dimens

/**
 * Funcion que permite reutilizar el campo de texto para la contraseña
 *
 * @param value Valor de la contraseña actual
 * @param onValueChange Callback para actualizar la contraseña al escribir
 * @param label Etiqueta del campo
 * @param isError Muestra un indicador de error en el campo
 * @param isSuccess Muestra un indicador de exito en el campo
 * @param supportingText Mensaje de ayuda o error debajo del campo
 * @param singleLine Indica si el campo solo puede tener una linea
 * @param modifier Modificador para personalizar el campo
 * @param dimens Dimensiones del campo
 */
@Composable
fun LevelUpPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    supportingText: (@Composable (() -> Unit))? = null,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    dimens: Dimens
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = levelUpTextFieldColors(isSuccess = isSuccess),
        label = {
            Text(
                text = label,
                fontSize = dimens.bodySize
            )
        },
        isError = isError,
        //Esto define como se muestra visualmente el texto escrito.
        //Si passwordvisible es true, el texto se muestra normal, si es false seran puntitos
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            /*
            * Trailing icon es el icono que aparece al lado derecho del campo de texto.
            * En este caso, alterna la visibilidad de la contraseña.
            * */
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            /*
            *   Boton clickeable que alterna entre dos iconos:
            *       Visibility: Ojo abierto
            *       VisibilityOff: Ojo cerrado
            *   Cambia el valor de passwordVisible cuando el usuario toca el icono (false, true)
            * */
            IconButton(
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(dimens.iconSize)
                )
            }
        },
        supportingText = supportingText,
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}